package io.codeone.framework.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class TypeStringUtilsTest {

    @Test
    void nullToString() {
        Assertions.assertNull(TypeStringUtils.toString((Class<?>) null));
        Assertions.assertNull(TypeStringUtils.toString((Method) null));
    }
}