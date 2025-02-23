package io.codeone.framework.common.log.util;

import io.codeone.framework.common.json.util.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class LogContentUtilsTest {

    @Test
    void toLogSafeKeyException() {
        Assertions.assertEquals("(TO_STRING_ERROR: io.codeone.framework.common.log.util.LogContentUtilsTestMalformedObj)",
                LogContentUtils.toLogSafeKey(new LogContentUtilsTestMalformedObj()));
    }

    @Test
    void toLogSafeValueJsonNotLoadedAndException() {
        try (MockedStatic<JsonUtils> mockedJsonUtils = Mockito.mockStatic(JsonUtils.class)) {
            mockedJsonUtils.when(JsonUtils::isLoaded).thenReturn(false);

            Assertions.assertEquals("(TO_STRING_ERROR: io.codeone.framework.common.log.util.LogContentUtilsTestMalformedObj)",
                    LogContentUtils.toLogSafeValue(new LogContentUtilsTestMalformedObj()));
        }
    }
}
