package io.codeone.framework.api.enableplugins;

import io.codeone.framework.api.enableplugins.domain.param.MyParam;
import io.codeone.framework.api.enableplugins.domain.service.TestApiEnablePluginsService;
import io.codeone.framework.api.exception.CommonErrors;
import io.codeone.framework.api.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiEnablePluginsTests {

    @Resource
    private TestApiEnablePluginsService testApiEnablePluginsService;

    @Test
    void testCheckArgs() {
        Result<Long> result = testApiEnablePluginsService.noArgIniting(new MyParam());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonErrors.INVALID_PARAM.getCode(), result.getErrorCode());
        Assertions.assertEquals("id is null", result.getErrorMessage());
    }

    @Test
    void testWithCustomMessage() {
        Result<Long> result = testApiEnablePluginsService.argIniting(new MyParam());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(0L, result.getData());
    }
}
