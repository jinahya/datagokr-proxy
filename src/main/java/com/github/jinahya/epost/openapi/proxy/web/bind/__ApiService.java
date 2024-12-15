package com.github.jinahya.epost.openapi.proxy.web.bind;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractResponseType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.context.ReactiveWebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
// https://github.com/spring-projects/spring-boot/issues/41862
abstract class __ApiService {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // -----------------------------------------------------------------------------------------------------------------
    @EventListener
    protected // required
    void onApplicationEvent(final ReactiveWebServerInitializedEvent event) {
        webServer = event.getWebServer();
    }

    // ------------------------------------------------------------------------------------------------------- webServer

    // ------------------------------------------------------------------------------------------------------- webClient
    private WebClient webClient() {
        assert webServer != null;
        if (webClient == null) {
            String localhost;
            try {
                localhost = InetAddress.getLocalHost().getHostAddress();
            } catch (final UnknownHostException uhe) {
                log.warn("failed to get localhost", uhe);
                localhost = "localhost";
            }
            return WebClient.builder()
                    .baseUrl("http://" + localhost + ':' + webServer.getPort())
                    .build();
        }
        return webClient;
    }

    /**
     * Exchanges a specified type of response for specified request.
     *
     * @param request      the request.
     * @param responseType the type of {@link RESPONSE}.
     * @param <RESPONSE>   response type parameter
     * @return a mono of {@link RESPONSE}.
     */
    @SuppressWarnings({
            "java:S119" // <RESPONSE ...>
    })
    protected <RESPONSE extends AbstractResponseType<RESPONSE>>
    Mono<RESPONSE> exchangeResponseFor(@Valid @NotNull final AbstractRequestType<?> request,
                                       @NotNull final Class<RESPONSE> responseType) {
        Objects.requireNonNull(request, "request is null");
        Objects.requireNonNull(responseType, "responseType is null");
        return request.exchange(webClient(), responseType);
    }

    @SuppressWarnings({
            "java:S119" // <REQUEST ...>
    })
    protected <
            REQUEST extends AbstractPairedRequestType<REQUEST, RESPONSE>,
            RESPONSE extends AbstractPairedResponseType<RESPONSE, REQUEST>>
    Mono<RESPONSE> exchangeResponse(@Valid @NotNull final REQUEST requestInstance) {
        Objects.requireNonNull(requestInstance, "requestInstance is null");
        return requestInstance.exchange(webClient());
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private WebServer webServer;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private WebClient webClient;
}
