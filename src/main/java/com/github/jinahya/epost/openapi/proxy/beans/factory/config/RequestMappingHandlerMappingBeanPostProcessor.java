package com.github.jinahya.epost.openapi.proxy.beans.factory.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

//@org.springframework.stereotype.Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
// https://stackoverflow.com/a/73439944/330457
class RequestMappingHandlerMappingBeanPostProcessor
//        implements BeanPostProcessor {
    {

//    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean instanceof RequestMappingHandlerMapping rmhm) {
            rmhm.setOrder(routePredicateHandlerMapping.getOrder() + 1);
        }
        return bean;
    }

    // -----------------------------------------------------------------------------------------------------------------
//    private final RoutePredicateHandlerMapping routePredicateHandlerMapping;
//    @Lazy
//    @Autowired
    private RoutePredicateHandlerMapping routePredicateHandlerMapping;
}
