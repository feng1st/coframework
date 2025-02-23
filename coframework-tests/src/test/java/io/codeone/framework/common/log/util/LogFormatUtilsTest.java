package io.codeone.framework.common.log.util;

import io.codeone.framework.common.json.util.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Method;

class LogFormatUtilsTest {

    @Test
    void initFormatJsonNotLoaded() throws Exception {
        try (MockedStatic<JsonUtils> mockedJsonUtils = Mockito.mockStatic(JsonUtils.class)) {
            mockedJsonUtils.when(JsonUtils::isLoaded).thenReturn(false);

            Method methodInitFormat = LogFormatUtils.class.getDeclaredMethod("initFormat");
            methodInitFormat.setAccessible(true);

            Assertions.assertEquals("logfmt", methodInitFormat.invoke(LogFormatUtils.class));
        }
    }
}