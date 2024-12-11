package com.github.jinahya.epost.openapi.proxy.web.server;

import com.github.jinahya.epost.openapi.proxy._reactor.util.context.ReactorContextUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * A web filter stores {@link org.springframework.http.server.reactive.ServerHttpRequest} to the
 * {@link reactor.util.context.Context}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see ReactorContextUtils
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
// https://github.com/reactor/reactor-core/issues/2572
// https://www.pkslow.com/docs/en/spring-webflux-get-request/#webfilter-fetch-and-save
// https://gist.github.com/Elyorbe/c7cc5f28d0fced86dbd054dce0a9e206
public class ReactiveRequestContextFilter
        implements WebFilter {

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(c -> ReactorContextUtils.putRequest(c, exchange.getRequest()));
    }
}
