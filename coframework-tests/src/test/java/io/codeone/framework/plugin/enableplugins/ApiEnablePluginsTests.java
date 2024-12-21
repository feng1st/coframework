package io.codeone.framework.plugin.enableplugins;

import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.plugin.enableplugins.domain.param.MyBizParam;
import io.codeone.framework.plugin.enableplugins.domain.service.TestApiEnablePluginsService;
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
        Result<Long> result = testApiEnablePluginsService.noArgIniting(new MyBizParam());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonCodes.INVALID_ARGS, result.getErrorCode());
        Assertions.assertEquals("id is null", result.getErrorMessage());
    }

    @Test
    void testWithCustomMessage() {
        Result<Long> result = testApiEnablePluginsService.argIniting(new MyBizParam());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(0L, result.getData());
    }
}
