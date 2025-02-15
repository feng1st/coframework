package io.codeone.framework.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class TypeNameUtilsTest {

    @Test
    void nullToString() {
        Assertions.assertNull(TypeNameUtils.toString((Class<?>) null));
        Assertions.assertNull(TypeNameUtils.toString((Method) null));
    }
}