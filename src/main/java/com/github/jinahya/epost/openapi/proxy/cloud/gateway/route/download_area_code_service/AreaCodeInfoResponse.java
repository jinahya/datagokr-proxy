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
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(name = AreaCodeInfoResponse.ROOT_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@Slf4j
public class AreaCodeInfoResponse
        extends AbstractPairedResponseType<AreaCodeInfoResponse, AreaCodeInfoRequest> {

    @Serial
    private static final long serialVersionUID = 9803126941295821L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String ROOT_NAME = "AreaCodeInfoResponse";

    static final String PROPERTY_NAME_FILE = "file";

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
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

    // --------------------------------------------------------------------------------------------------- super.wrapped
    @Schema(hidden = true)
    @JsonProperty(ROOT_NAME)
    @Override
    public AreaCodeInfoResponse getWrapped() {
        return super.getWrapped();
    }

    // -------------------------------------------------------------------------------------------------- request.dwldSe
    @Nullable
    public String getRequestDwldSe() {
        return Optional.ofNullable(getRequestInstance())
                .map(AreaCodeInfoRequest::getDwldSe)
                .orElse(null);
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
     * Downloads this response's {@value #PROPERTY_NAME_FILE} content to specified file.
     *
     * @param target the target file to which this response's {@value #PROPERTY_NAME_FILE} is downloaded.
     * @return the number of bytes downloaded to the {@code target}.
     * @throws IOException if an I/O error occurs.
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
