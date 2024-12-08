package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;

@XmlRootElement(name = AreaCodeInfoResponse.ROOT_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@Slf4j
public class AreaCodeInfoResponse
        extends AbstractPairedResponseType<AreaCodeInfoResponse, AreaCodeInfoRequest> {

    @Serial
    private static final long serialVersionUID = 9803126941295821L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String ROOT_NAME = "AreaCodeInfoResponse";

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    public AreaCodeInfoResponse() {
        super(AreaCodeInfoRequest.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + '{' +
                "file='" + file + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final var that = (AreaCodeInfoResponse) obj;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), file);
    }

    // ---------------------------------------------------------------------------------------------------- cmmMsgHeader

    // --------------------------------------------------------------------------------------------------------- wrapped
    @Schema(hidden = true)
    @JsonProperty(ROOT_NAME)
    @Override
    public AreaCodeInfoResponse getWrapped() {
        return super.getWrapped();
    }

    // ------------------------------------------------------------------------------------------------------------ file

    /**
     * Returns current value of {@code file} property.
     *
     * @return current value of the {@code file} property.
     */
    public String getFile() {
        return file;
    }

    /**
     * Downloads this response's {@code file} to specified file.
     *
     * @param target the target file to which this response's {@code file} is downloaded.
     * @return the number of bytes downloaded to the {@code target}.
     * @see AreaCodeInfoUtils#downloadFile(AreaCodeInfoResponse, File)
     */
    public long downloadFile(final File target) throws IOException {
        Objects.requireNonNull(target, "target is null");
        return AreaCodeInfoUtils.downloadFile(this, target);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    @JsonProperty(required = true)
    @XmlElement(required = true, nillable = false)
    private String file;
}
