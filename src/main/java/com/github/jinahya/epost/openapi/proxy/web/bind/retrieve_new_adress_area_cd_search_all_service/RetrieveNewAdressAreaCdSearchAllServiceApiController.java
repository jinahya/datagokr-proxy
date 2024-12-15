package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_search_all_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllResponse.NewAddressListAreaCdSearchAll;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import static com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_search_all_service._RetrieveNewAdressAreCdSearchAllServiceApiConstants.REQUEST_PARAM_SRCHWRD;
import static com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_search_all_service._RetrieveNewAdressAreCdSearchAllServiceApiConstants.REQUEST_URI_SEARCH;

//@Tag(name = _RetrieveNewAdressAreCdSearchAllServiceApiConstants.TAG)
@Validated
@RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class RetrieveNewAdressAreaCdSearchAllServiceApiController
        extends _ApiController<RetrieveNewAdressAreaCdSearchAllServiceApiService> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    private Iterable<Link> links(final NewAddressListAreaCdSearchAll content) {
        return List.of(
        );
    }

    private EntityModel<NewAddressListAreaCdSearchAll> model(final NewAddressListAreaCdSearchAll content) {
        return EntityModel.of(content, links(content));
    }

    @GetMapping(
            path = REQUEST_URI_SEARCH,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE,
                    MediaTypes.HAL_JSON_VALUE
            }
    )
    Flux<EntityModel<NewAddressListAreaCdSearchAll>> search(
            final ServerWebExchange exchange,
            @RequestParam(REQUEST_PARAM_SRCHWRD) final String srchwrd) {
        final var total = new AtomicReference<Integer>();
        final var count = new LongAdder();
        return Mono.just(NewAddressListAreaCdSearchAllRequest.of(
                        null,
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
                .switchOnFirst((s, f) -> {
                    if (s.hasValue()) {
                        final var cmmMsgHeader = s.get().getCmmMsgHeader();
                        total.compareAndSet(null, cmmMsgHeader.getTotalCount());
                    }
                    return f;
                })
                .flatMap(r -> Flux.fromIterable(r.getNewAddressListAreaCdSearchAll()))
                .<NewAddressListAreaCdSearchAll>handle((e, s) -> {
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
