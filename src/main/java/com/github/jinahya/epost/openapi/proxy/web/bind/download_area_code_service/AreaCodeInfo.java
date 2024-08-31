package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind.AbstractWrappingModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AreaCodeInfo
        extends AbstractWrappingModel<AreaCodeInfo, AreaCodeInfoResponse> {

    @Serial
    private static final long serialVersionUID = 5639131955588158747L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    public static AreaCodeInfo newInstance(final AreaCodeInfoResponse wrapped) {
        return newInstance(AreaCodeInfo::new, wrapped);
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ---------------------------------------------------------------------------------------------------------- wrapped

    @Override
    public void setWrapped(final AreaCodeInfoResponse response) {
        super.setWrapped(response);
        setDwldSe(
                Optional.ofNullable(getWrapped())
                        .map(AbstractPairedResponseType::getRequestInstance)
                        .map(AreaCodeInfoRequest::getDwldSe)
                        .orElse(null)
        );
        setFile(
                Optional.ofNullable(getWrapped())
                        .map(AreaCodeInfoResponse::getFile)
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String dwldSe;

    @NotBlank
    private String file;
}
