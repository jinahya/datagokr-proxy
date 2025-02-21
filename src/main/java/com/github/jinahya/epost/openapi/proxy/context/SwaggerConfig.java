package com.github.jinahya.epost.openapi.proxy.context;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.utils.Constants;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class SwaggerConfig {

    //    @org.springframework.context.annotation.Bean
    public Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> apis(
            final RouteDefinitionLocator locator,
            final SwaggerUiConfigParameters swaggerUiConfigParameters) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        definitions.stream()
//                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                    name, Constants.DEFAULT_API_DOCS_URL + "/" + name, null);
                    urls.add(swaggerUrl);
                });
        swaggerUiConfigParameters.setUrls(urls);
        return urls;
    }

    private final RouteLocator routeLocator;
}
