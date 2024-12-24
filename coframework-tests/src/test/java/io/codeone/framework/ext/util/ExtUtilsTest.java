package io.codeone.framework.ext.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

class ExtUtilsTest {

    @Test
    public void getAllExtensibleInterfaces() {
        Assertions.assertEquals(Collections.emptyList(), ExtUtils.getAllExtensibleInterfaces(ArrayList.class));
    }
}
