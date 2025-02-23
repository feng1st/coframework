package io.codeone.framework.common.log.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogFmtUtilsTest {

    @Test
    void encodeNull() {
        Assertions.assertEquals("null", LogFmtUtils.encodeLogFmtValue(null));
    }
}