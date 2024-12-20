package io.codeone.framework.api.plugin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArgValidatingApiPluginTest {

    @Autowired
    private ArgValidatingApiPluginTestService argValidatingApiPluginTestService;

    @Test
    public void apiParamWithUserId() {
        ArgValidatingApiPluginTestApiParam param = new ArgValidatingApiPluginTestApiParam();
        param.setUserId(0L);
        argValidatingApiPluginTestService.apiParam(param);
    }

    @Test
    public void apiParamWithoutUserId() {
        ArgValidatingApiPluginTestApiParam param = new ArgValidatingApiPluginTestApiParam();
        Assertions.assertEquals("userId is required",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> argValidatingApiPluginTestService.apiParam(param)).getMessage());
    }

    @Test
    public void nonApiParam() {
        ArgValidatingApiPluginTestNonApiParam param = new ArgValidatingApiPluginTestNonApiParam();
        argValidatingApiPluginTestService.nonApiParam(param);
    }
}