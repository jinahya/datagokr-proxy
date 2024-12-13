package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy._hidden._com.springframework.web.util._UriComponents_Utils;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest.DwldSe;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController;
import com.github.jinahya.epost.openapi.proxy.web.reactive.function.client.WebClientUtils;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Tag(name = _DownloadAreaCodeServiceApiConstants.TAG)
@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class DownloadAreaCodeServiceApiController
        extends _ApiController<DownloadAreaCodeServiceApiService> {

    private static Iterable<Link> links(final AreaCodeInfoResponse response) {
        final var request = response.getRequestInstance();
        return List.of(
                Link.of(UriComponentsBuilder.fromPath(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE)
                                .build(request.getDwldSe().value())
                                .toString())
                        .withSelfRel(),
                Link.of(UriComponentsBuilder.fromPath(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_FILE_CONTENT)
                                .build(request.getDwldSe().value())
                                .toString())
                        .withRel(_DownloadAreaCodeServiceApiConstants.REL_FILE_CONTENT)
        );
    }

    private EntityModel<AreaCodeInfoResponse> model(final AreaCodeInfoResponse response) {
        response.setCmmMsgHeader(null);
        return EntityModel.of(response, links(response));
    }

    private Publisher<DataBuffer> getFileContentPublisher(final DwldSe dwldSe,
                                                          final Consumer<? super String> filenameConsumer) {
        return service().exchange(AreaCodeInfoRequest.of(dwldSe))
                .map(AreaCodeInfoResponse::getFile)
                .flatMapMany(f -> {
                    final String filename = _UriComponents_Utils.getFile(f, true).orElse(null);
                    filenameConsumer.accept(filename);
                    return WebClientUtils.retrieveBodyToFlux(f, DataBuffer.class);
                });
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // --------------------------------------------------------------------------------------------------------------- /

    /**
     * Reads all responses for all values {@link DwldSe} enum.
     *
     * @param exchange a server web exchange.
     * @return a flux of all responses for all values of {@link DwldSe} enum.
     */
    @ApiResponse(content = {
            @Content(schema = @Schema(implementation = AreaCodeInfoResponse.class))
    })
    @GetMapping(
            path = {
                    _DownloadAreaCodeServiceApiConstants.REQUEST_URI_AREA_CODE_INFO
            },
            produces = {
                    MediaType.APPLICATION_NDJSON_VALUE,
                    MediaTypes.HAL_JSON_VALUE
            }
    )
    Flux<EntityModel<AreaCodeInfoResponse>> readAreaCodeInfo(final ServerWebExchange exchange) {
        return Flux.fromArray(DwldSe.values())
                .flatMapSequential(v -> service().exchange(AreaCodeInfoRequest.of(v)))
                .map(this::model);
    }

    // ------------------------------------------------------------------------------------------------------- /{dwldSe}

    /**
     * Reads a response for specified value of {@link DwldSe} enum.
     *
     * @param exchange a server web exchange.
     * @param dwldSe   the value of {@link DwldSe} enum for the response.
     * @return a mono of the responses for the {@code dwldSe}.
     */
    @ApiResponse(content = {
            @Content(schema = @Schema(implementation = AreaCodeInfoResponse.class))
    })
    @GetMapping(
            path = {
                    _DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE
            },
            produces = {
                    MediaTypes.HAL_JSON_VALUE
            }
    )
    Mono<EntityModel<AreaCodeInfoResponse>> readAreaCodeInfo(
            final ServerWebExchange exchange,
            @PathVariable(_DownloadAreaCodeServiceApiConstants.PATH_NAME_DWLD_SE) final DwldSe dwldSe) {
        return service()
                .exchange(AreaCodeInfoRequest.of(dwldSe))
                .map(r -> r.cmmMsgHeader(null))
                .map(this::model);
    }

    // -------------------------------------------------------------------------------------------------- /{dwldSe}/file

    /**
     * Reads the {@code $.file} value of the {@link AreaCodeInfoResponse} retrieved for specified {@link DwldSe}.
     *
     * @param exchange a server web exchange.
     * @param dwldSe   the value of {@link DwldSe} to download.
     * @return a mono of {@code $.file} value of the {@link AreaCodeInfoResponse} for {@code dwldSe}.
     */
    @GetMapping(
            path = {
                    _DownloadAreaCodeServiceApiConstants.REQUEST_URI_FILE
            },
            produces = {
                    MediaType.TEXT_PLAIN_VALUE
            }
    )
    Mono<String> readAreaCodeInfoFile(
            final ServerWebExchange exchange,
            @PathVariable(_DownloadAreaCodeServiceApiConstants.PATH_NAME_DWLD_SE) final DwldSe dwldSe) {
        return service()
                .exchange(AreaCodeInfoRequest.of(dwldSe))
                .map(v -> v.cmmMsgHeader(null))
                .map(AreaCodeInfoResponse::getFile);
    }

    // ------------------------------------------------------------------------------------------ /{dwldSe}/file/content

    /**
     * Reads(downloads) the {@link AreaCodeInfoResponse#getFile() file} content of the response for specified value of
     * {@link DwldSe}.
     *
     * @param exchange a server web exchange.
     * @param dwldSe   the value of {@link DwldSe} to download.
     * @param attach   a flag for using {@value HttpHeaders#CONTENT_DISPOSITION} header with {@code attachment}.
     * @param filename a value for the {@code filename} element of the {@value HttpHeaders#CONTENT_DISPOSITION} header.
     * @return a flux of data buffers.
     */
    @GetMapping(
            path = {
                    _DownloadAreaCodeServiceApiConstants.REQUEST_URI_FILE_CONTENT
            },
            produces = {
                    MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    "application/zip"
            }
    )
    Publisher<DataBuffer> readAreaCodeInfoFileContent(
            final ServerWebExchange exchange,
            @PathVariable(_DownloadAreaCodeServiceApiConstants.PATH_NAME_DWLD_SE) final DwldSe dwldSe,
            @RequestParam(value = _DownloadAreaCodeServiceApiConstants.PARAM_ATTACH, required = false)
            final Boolean attach,
            @RequestParam(value = _DownloadAreaCodeServiceApiConstants.PARAM_FILENAME, required = false)
            final String filename) {
        return getFileContentPublisher(
                dwldSe,
                f -> {
                    // attach 가 true 이고 filename 혹은 f 가 present 할 경우,
                    // Content-Disposition: attachment; filename="v" 헤더를 붙인다.
                    if (attach == null || !attach) {
                        return;
                    }
                    Optional.ofNullable(filename)
                            .map(String::strip)
                            .filter(v -> !v.isBlank())
                            .or(() -> Optional.ofNullable(f).map(String::strip).filter(v -> !v.isBlank()))
                            .ifPresent(v -> {
                                beforeCommit(exchange.getResponse(), r -> {
                                    // https://stackoverflow.com/a/20933751/330457
                                    // https://stackoverflow.com/q/93551/330457
                                    final var encoded = URLEncoder.encode(v, StandardCharsets.UTF_8);
                                    if (encoded.equals(v)) {
                                        r.getHeaders().setContentDisposition(
                                                ContentDisposition.attachment().filename(encoded).build()
                                        );
                                    } else {
                                        final var filename1 = "filename=\"" + dwldSe.value() + ".zip\"";
                                        // uppercase 'UTF-8' doesn't work, at least, with Postman
                                        final var filename2 = "filename*=utf-8''" + encoded;
                                        final var headerVal = "attachment; " + filename1 + "; " + filename2;
                                        r.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, headerVal);
                                    }
                                });
                            });
                }
        );
    }
}
