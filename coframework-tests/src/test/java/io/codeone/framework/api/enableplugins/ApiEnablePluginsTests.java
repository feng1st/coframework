package io.codeone.framework.api.enableplugins;

import io.codeone.framework.api.enableplugins.domain.param.MyParam;
import io.codeone.framework.api.enableplugins.domain.service.TestApiEnablePluginsService;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiEnablePluginsTests {

    @Autowired
    private TestApiEnablePluginsService testApiEnablePluginsService;

    @Test
    void testCheckArgs() {
        Result<Long> result = testApiEnablePluginsService.noArgIniting(new MyParam());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonCodes.INVALID_ARGS, result.getErrorCode());
        Assertions.assertEquals("id is null", result.getErrorMessage());
    }

    @Test
    void testWithCustomMessage() {
        Result<Long> result = testApiEnablePluginsService.argIniting(new MyParam());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(0L, result.getData());
    }
}
