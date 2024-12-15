package com.github.jinahya.epost.openapi.proxy.web.bind;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service._NoOp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.platform.commons.support.ReflectionSupport;
import org.springframework.core.ResolvableType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

import static com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractTypeUtils.unmarshalNoNamespacedInstance;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SuppressWarnings({
        "java:S119" // Type parameter names should comply with a naming convention
})
public abstract class _ApiController_SpringBootTest<
        CONTROLLER extends _ApiController<SERVICE>,
        SERVICE extends _ApiService>
        extends __ApiController_SpringBootTest<CONTROLLER> {

    // ------------------------------------------------------------------------------------------------------ CONTROLLER

    // --------------------------------------------------------------------------------------------------------- SERVICE
    @SuppressWarnings({
            "unchecked"
    })
    protected Class<SERVICE> serviceClass() {
        return (Class<SERVICE>) ResolvableType
                .forType(getClass())
                .as(_ApiController_SpringBootTest.class)
                .getGeneric(1)
                .resolve();
    }

    // ------------------------------------------------------------------------------------------------- serviceInstance
    // https://stackoverflow.com/a/69214093/330457
    protected <REQUEST extends AbstractRequestType<REQUEST>, RESPONSE extends AbstractResponseType<RESPONSE>>
    void stubExchangeResponseFor(final Class<REQUEST> requestClass, final Class<RESPONSE> responseClass,
                                 final String resName) {
        when(serviceInstance().exchangeResponseFor(notNull(requestClass), responseClass)).thenAnswer(i -> {
            try (var r = _NoOp.class.getResourceAsStream(resName)) {
                return Mono.just(unmarshalNoNamespacedInstance(responseClass, r));
            }
        });
    }

    protected <REQUEST extends AbstractPairedRequestType<REQUEST, RESPONSE>,
            RESPONSE extends AbstractPairedResponseType<RESPONSE, REQUEST>>
    void stubExchangeResponse(final Class<REQUEST> requestClass, final Class<RESPONSE> responseClass,
                              final String resName) throws Exception {
        final RESPONSE response;
        try (var r = _NoOp.class.getResourceAsStream(resName)) {
            response = unmarshalNoNamespacedInstance(responseClass, r);
        }
//        doReturn(Mono.just(response))
//                .when(serviceInstance)
//                .exchangeResponse(notNull(requestClass));
//        when(serviceInstance.exchangeResponse(any(requestClass)))
//                .thenReturn(Mono.just(response));
//        when(serviceInstance.exchangeResponse(any(requestClass)))
//                .thenReturn(Mono.just(response));
        final var method = _ApiService.class.getDeclaredMethod("exchangeResponse", AbstractPairedRequestType.class);
        method.setAccessible(true);
        ReflectionSupport.invokeMethod(
                method,
                doReturn(Mono.just(response)).when(serviceInstance)
//                when(serviceInstance.exchangeResponse(notNull(requestClass))).thenReturn(Mono.<RESPONSE>just(response))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @MockitoBean
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PROTECTED)
    private SERVICE serviceInstance;
}