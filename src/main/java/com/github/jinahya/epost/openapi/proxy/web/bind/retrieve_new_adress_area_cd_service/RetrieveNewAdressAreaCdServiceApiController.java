package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service.NewAddressListAreaCdRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service.NewAddressListAreaCdResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import static com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service._RetrieveNewAdressAreaCdServiceApiConstants.PATH_SEGMENT_NAME_SEARCH_SE;
import static com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service._RetrieveNewAdressAreaCdServiceApiConstants.PATH_SEGMENT_NAME_SRCHWRD;
import static com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service._RetrieveNewAdressAreaCdServiceApiConstants.REQUEST_URI__SEARCH_SE__SRCHWRD;

@Hidden
@Tag(name = _RetrieveNewAdressAreaCdServiceApiConstants.TAG)
@Validated
@RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class RetrieveNewAdressAreaCdServiceApiController
        extends _ApiController<RetrieveNewAdressAreaCdServiceApiService> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // -----------------------------------------------------------------------------------------------------------------
    private Iterable<Link> links(final NewAddressListAreaCdResponse.NewAddressListAreaCd content) {
        return List.of(
        );
    }

    private EntityModel<NewAddressListAreaCdResponse.NewAddressListAreaCd> model(
            final NewAddressListAreaCdResponse.NewAddressListAreaCd content) {
        return EntityModel.of(content, links(content));
    }

    // -----------------------------------------------------------------------------------------------------------------
    @GetMapping(
            path = REQUEST_URI__SEARCH_SE__SRCHWRD,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE,
                    MediaTypes.HAL_JSON_VALUE
            }
    )
    Flux<EntityModel<NewAddressListAreaCdResponse.NewAddressListAreaCd>> search(
            final ServerWebExchange exchange,
            @PathVariable(PATH_SEGMENT_NAME_SEARCH_SE) final NewAddressListAreaCdRequest.SearchSe searchSe,
            @PathVariable(PATH_SEGMENT_NAME_SRCHWRD) final String srchwrd) {
        final var total = new AtomicReference<Integer>();
        final var count = new LongAdder();
        return Mono.just(NewAddressListAreaCdRequest.of(
                        null,
                        searchSe,
                        srchwrd,
                        32,
                        1
                ))
                .expand(r -> Mono.just(r.forNextPage()))
                .flatMapSequential(
                        r -> service().exchange(r),
                        5,
                        1
                )
                .doOnError(t -> {
                    log.error("failed to exchange", t);
                })
                .onErrorComplete()
                .switchOnFirst((s, f) -> {
                    if (s.hasValue()) {
                        final var response = s.get();
                        assert response != null;
                        final var cmmMsgHeader = response.getCmmMsgHeader();
                        total.compareAndSet(null, cmmMsgHeader.getTotalCount());
                    }
                    return f;
                })
                .flatMap(r -> Flux.fromIterable(r.getNewAddressListAreaCdList()))
                .<NewAddressListAreaCdResponse.NewAddressListAreaCd>handle((e, s) -> {
                    count.increment();
                    final var t = total.get();
                    final var c = count.sum();
                    if (t == null || c <= t) {
                        s.next(e);
                    } else {
                        s.complete();
                    }
                })
                .map(this::model);
    }
}
