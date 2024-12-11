package com.github.jinahya.epost.openapi.proxy._java.misc.invoke;

import com.github.jinahya.epost.openapi.proxy._java.lang.invoke.LookupUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LookupUtilsTest {

    @Test
    void __() {
        final var lookup = LookupUtils.privateLookup(getClass());
        assertThat(lookup).isNotNull();
    }
}
