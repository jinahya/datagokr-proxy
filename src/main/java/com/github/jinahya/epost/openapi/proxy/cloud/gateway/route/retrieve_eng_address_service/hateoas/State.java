package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.hateoas;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.StateEngListResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class State
        extends RepresentationModel<State> {

    static String getHref(final String name) {
        return _Constants.REQUEST_URI_STATES + '/' + name;
    }

    static String getHref(final State state) {
        return getHref(state.getName());
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    public static State from(final StateEngListResponse.StateEngList stateEngList) {
        Objects.requireNonNull(stateEngList, "stateEngList is null");
        final var instance = new State();
        instance.setName(stateEngList.getStateEngName());
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ----------------------------------------------------------------------------------------------------- super.links
    public State addLinks() {
        add(
                Link.of(getHref(this))
                        .withRel(IanaLinkRelations.SELF)
        );
        add(
                Link.of(getHref(this) + '/' + _Constants.REL_CITIES)
                        .withRel(_Constants.REL_CITIES)
        );
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String name;
}
