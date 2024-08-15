package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_eng_address_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.*;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Hidden
@Tag(name = _RetrieveEngAddressServiceApiConstants.TAG)
@Validated
@RestController
@Slf4j
class _RetrieveEngAddressServiceApiController
        extends _ApiController {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    _RetrieveEngAddressServiceApiController() {
        super();
    }

    // ----------------------------------------------------------------------------------------------------- /.../states
    private Flux<State> getStatesPublisher() {
        return new StateEngListRequest()
                .exchange(webClient())
                .flatMapMany(r -> Flux.fromIterable(r.getStateEngList()))
                .map(State::instanceOf)
                .map(State::addLinks);
    }

    @Operation(summary = "Reads all states.")
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_STATES,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readStates(final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getStatesPublisher(),
                State.class
        );
    }

    @Operation(summary = "Reads a state.")
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_STATE,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readState(
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getStatesPublisher().filter(s -> Objects.equals(s.getWrapped().getStateEngName(), stateName)),
                State.class
        );
    }

    // ---------------------------------------------------------------------------------- /.../states/{stateName}/cities
    private Flux<City> getCitiesPublisher(final String stateName) {
        return CityEngListRequest.of(null, stateName)
                .exchange(webClient())
                .flatMapMany(r -> Flux.fromIterable(r.getCityEngList()))
                .map(cel -> City.instanceOf(null, cel))
                .map(City::addLinks);
    }

    @Operation(summary = "Reads all cities.", description = "Reads all cities in specified state.")
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_CITIES,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readCities(
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getCitiesPublisher(stateName),
                City.class
        );
    }

    @Operation(
            summary = "Reads a city.",
            description = "Reads a specific city in specified state."
    )
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_CITY,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readCity(
            @Parameter(description = "the name of the state")
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @Parameter(description = "the name of the city")
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getCitiesPublisher(stateName).filter(c -> Objects.equals(c.getWrapped().getCityEngName(), cityName)),
                City.class
        );
    }

    // ----------------------------------------------------------------- /.../states/{stateName}/cities/{cityName}/roads
    Flux<Road> getRoadPublisher(final String stateName, final String cityName) {
        return RoadEngFirstNameListRequest.of(null, stateName, cityName)
                .exchange(webClient())
                .flatMapMany(refnlr -> Flux.fromIterable(refnlr.getRoadEngFirstNameList()))
                .map(refnl -> RoadEngListRequest.of(null, stateName, cityName, refnl.getRoadEngFirstName()))
                .flatMapSequential(relr -> relr.exchange(webClient()), 5)
                .flatMap(relr -> Flux.fromIterable(relr.getRoadEngList()))
                .map(rel -> Road.instanceOf(stateName, cityName, rel))
                .map(Road::addLinks);
    }

    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_ROADS,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readRoads(
            @NotBlank
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getRoadPublisher(stateName, cityName),
                Road.class
        );
    }

    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_ROAD,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readRoad(
            @NotBlank
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_ROAD_NAME) final String roadName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getRoadPublisher(stateName, cityName)
                        .filter(r -> Objects.equals(r.getWrapped().getRoadEngName(), roadName)),
                Road.class
        );
    }

    // -------------------------------------------- /.../states/{stateName}/cities/{cityName}/roads/{roadName}/addresses
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_ROAD_ADDRESSES,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readRoadAddresses(
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_ROAD_NAME) final String roadName,
            final ServerHttpResponse response) {
        final var total = new AtomicInteger();
        final var count = new LongAdder();
        final var data = Mono.just(RoadAddressEngSearchListRequest.of(
                        null,
                        stateName,
                        cityName,
                        null,
                        roadName,
                        null,
                        32,
                        1
                ))
                .expand(r -> Mono.just(r.forNextPage()))
                .flatMapSequential(r -> r.exchange(webClient()), 5, 1)
                .switchOnFirst((s, f) -> {
                    final var cmmMsgHeader = s.get().getCmmMsgHeader();
                    total.set(cmmMsgHeader.getTotalCount());
                    return f;
                })
                .flatMap(r -> {
                    final var list = r.getRoadAddressEngSearchList();
                    count.add(list.size());
                    return Flux.fromIterable(list)
                            .map(RoadAddress::instanceOf)
                            .map(RoadAddress::addLinks);
                })
                .takeWhile(e -> count.sum() < total.longValue());
        return writeNdjsonResponseWith(response, data, RoadAddress.class);
    }

    // ------------------------------------------------------------- /.../states/{stateName}/cities/{cityName}/districts
    private Flux<District> getDistrictPublisher(final String stateName, final String cityName) {
        return DistrictEngFirstNameListRequest.of(null, stateName, cityName)
                .exchange(webClient())
                .flatMapMany(defnlr -> Flux.fromIterable(defnlr.getDistrictEngFirstNameList()))
                .map(refnl -> DistrictEngListRequest.of(null, stateName, cityName, refnl.getDistrictEngFirstName()))
                .flatMapSequential(delr -> delr.exchange(webClient()), 5)
                .flatMap(relr -> Flux.fromIterable(relr.getDistrictEngList()))
                .map(rel -> District.districtOf(stateName, cityName, rel))
                .map(District::addLinks);
    }

    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_DISTRICTS,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readDistricts(
            @NotBlank
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getDistrictPublisher(stateName, cityName),
                District.class
        );
    }

    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_DISTRICT,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readDistricts(
            @NotBlank
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            @NotBlank
            @PathVariable(
                    name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_DISTRICT_NAME) final String districtName,
            final ServerHttpResponse response) {
        return writeNdjsonResponseWith(
                response,
                getDistrictPublisher(stateName, cityName)
                        .filter(d -> Objects.equals(d.getWrapped().getDistrictEngName(), districtName)),
                District.class
        );
    }

    // ------------------------------------ /.../states/{stateName}/cities/{cityName}/districts/{districtName}/addresses
    @GetMapping(
            path = _RetrieveEngAddressServiceApiConstants.REQUEST_URI_DISTRICT_ADDRESSES,
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE
            }
    )
    Mono<Void> readDistrictAddresses(
            @NotBlank
            @PathVariable(_RetrieveEngAddressServiceApiConstants.PATH_NAME_STATE_NAME) final String stateName,
            @NotBlank
            @PathVariable(name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_CITY_NAME) final String cityName,
            @NotBlank
            @PathVariable(
                    name = _RetrieveEngAddressServiceApiConstants.PATH_NAME_DISTRICT_NAME) final String districtName,
            final ServerHttpResponse response) {
        final var total = new AtomicInteger();
        final var count = new LongAdder();
        final var data = Mono.just(LandAddressEngSearchListRequest.of(
                        null,
                        stateName,
                        cityName,
                        null,
                        districtName,
                        null,
                        32,
                        1
                ))
                .expand(r -> Mono.just(r.forNextPage()))
                .flatMapSequential(r -> r.exchange(webClient()), 5, 1)
                .switchOnFirst((s, f) -> {
                    final var cmmMsgHeader = s.get().getCmmMsgHeader();
                    total.set(cmmMsgHeader.getTotalCount());
                    return f;
                })
                .flatMap(r -> {
                             final var list = r.getLandAddressEngSearchList();
                             count.add(list.size());
                             return Flux.fromIterable(list)
                                     .map(laesl -> DistrictAddress.instanceOf(stateName, cityName, districtName, laesl))
                                     .map(DistrictAddress::addLinks);
                         }
                )
                .takeWhile(e -> count.sum() < total.longValue());
        return writeNdjsonResponseWith(response, data, DistrictAddress.class);
    }
}
