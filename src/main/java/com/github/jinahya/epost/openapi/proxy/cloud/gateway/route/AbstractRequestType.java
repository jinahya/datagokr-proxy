package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An abstract class for binding request messages.
 *
 * @param <SELF> self type parameter
 * @see AbstractResponseType
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings({
        "java:S119" // <SELF ...>
})
public abstract class AbstractRequestType<SELF extends AbstractRequestType<SELF>>
        extends AbstractType<SELF> {

    @Serial
    private static final long serialVersionUID = -5536339965586422829L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    protected static <T extends AbstractRequestType<?>> T of(final Supplier<? extends T> initializer,
                                                             final String serviceKey) {
        final var instance = of(initializer);
        instance.setServiceKey(serviceKey);
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected AbstractRequestType() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Exchanges an instance of response message of specified type, using specified web client.
     *
     * @param webClient    the web client.
     * @param responseType the response type to exchange.
     * @param <T>          response type parameter
     * @return an instance of {@code responseType} exchanged.
     */
    public <T extends AbstractResponseType<T>> Mono<T> exchange(final WebClient webClient,
                                                                final Class<T> responseType) {
        Objects.requireNonNull(webClient, "webClient is null");
        Objects.requireNonNull(responseType, "responseType is null");
        @SuppressWarnings({"unchecked"})
        final var self = (SELF) this;
        return webClient
                .method(httpMethod)
                .uri(b -> {
                    uriConfigurer.accept(self, b);
                    return b.build();
                })
                .headers(headersConfigurer)
                .exchangeToMono(r -> r.bodyToMono(responseType).map(AbstractResponseType::get))
                .handle((r, s) -> {
                    final var cmmMsgHeader = r.getCmmMsgHeader();
                    if (cmmMsgHeader == null || !cmmMsgHeader.isSucceeded()) {
                        s.error(new RuntimeException("unsuccessful result: " + r));
                    } else {
                        s.next(r);
                    }
                })
                ;
    }

    // ------------------------------------------------------------------------------------------------------ serviceKey

    /**
     * Returns current value of {@code serviceKey} property.
     *
     * @return current value of {@code serviceKey} property.
     */
    public String getServiceKey() {
        return serviceKey;
    }

    /**
     * Replaces current value of {@code serviceKey} property with specified value.
     *
     * @param serviceKey new value for the {@code serviceKey} property.
     * @see #serviceKey(String)
     */
    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    /**
     * Replaces current value of {@code serviceKey} property with specified value, and returns this object.
     *
     * @param serviceKey new value for the {@code serviceKey} property.
     * @return this object.
     * @see #setServiceKey(String)
     */
    @SuppressWarnings({"unchecked"})
    public final SELF serviceKey(final String serviceKey) {
        setServiceKey(serviceKey);
        return (SELF) this;
    }

    // ------------------------------------------------------------------------------------------------------ httpMethod

    // --------------------------------------------------------------------------------------------------- uriConfigurer
    public BiConsumer<? super SELF, ? super UriBuilder> getUriConfigurer() {
        return uriConfigurer;
    }

    public void setUriConfigurer(final BiConsumer<? super SELF, ? super UriBuilder> uriBuilder) {
        this.uriConfigurer = Objects.requireNonNull(uriBuilder, "uriBuilder is null");
    }

    protected void setUriConfigurer(final BiConsumer<? super SELF, ? super UriBuilder> uriBuilder,
                                    final boolean wrap) {
        Objects.requireNonNull(uriBuilder, "uriBuilder is null");
        if (!wrap) {
            setUriConfigurer(uriBuilder);
            return;
        }
        final var wrapper = getUriConfigurer();
        this.uriConfigurer = (s, b) -> {
            uriBuilder.accept(s, b);
            wrapper.accept(s, b);
        };
    }

    public UriBuilder configureUri(final UriBuilder builder) {
        @SuppressWarnings({"unchecked"})
        final var self = (SELF) this;
        getUriConfigurer().accept(self, builder);
        return builder;
    }

    public URI buildUri(final UriBuilder builder) {
        return configureUri(builder).build();
    }

    // ----------------------------------------------------------------------------------------------- headersConfigurer
    public void configureHeaders(final HttpHeaders headers) {
        headersConfigurer.accept(headers);
    }

    // -----------------------------------------------------------------------------------------------------------------
//    @Schema(hidden = true)
    private String serviceKey;

    // -----------------------------------------------------------------------------------------------------------------
    @JsonIgnore
    @XmlTransient
    @EqualsAndHashCode.Exclude
    private HttpMethod httpMethod = HttpMethod.GET;

    @JsonIgnore
    @XmlTransient
    @EqualsAndHashCode.Exclude
    private BiConsumer<? super SELF, ? super UriBuilder> uriConfigurer = (s, b) -> {
        b.queryParamIfPresent(
                        _RouteConstants.PARAM_SERVICE_KEY,
                        Optional.ofNullable(s.getServiceKey())
                )
                .build();
    };

    @JsonIgnore
    @XmlTransient
    @EqualsAndHashCode.Exclude
    private Consumer<HttpHeaders> headersConfigurer = httpHeaders -> {
    };
}
