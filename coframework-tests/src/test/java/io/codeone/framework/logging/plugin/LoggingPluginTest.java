package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.BaseLoggingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingPluginTest extends BaseLoggingTest {

    @Autowired
    private LoggingPluginTestService loggingPluginTestService;

    @Test
    public void apiSuccess() {
        loggingPluginTestService.apiSuccess();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.apiSuccess\",\"success\":true,\"elapsed\":0,\"result\":\"success\"}");
    }

    @Test
    public void apiFailure() {
        loggingPluginTestService.apiFailure();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.apiFailure\",\"success\":false,\"code\":\"FAILURE\",\"message\":\"Failure\",\"elapsed\":0}");
    }

    @Test
    public void apiException() {
        loggingPluginTestService.apiException();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.apiException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Exception\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Exception\"}");
    }

    @Test
    public void loggingSuccess() {
        loggingPluginTestService.loggingSuccess();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"result\":\"success\"}");
    }

    @Test
    public void loggingFailure() {
        loggingPluginTestService.loggingFailure();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingFailure\",\"success\":false,\"code\":\"FAILURE\",\"message\":\"Failure\",\"elapsed\":0}");
    }

    @Test
    public void loggingException() {
        loggingPluginTestService.loggingException();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Exception\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Exception\"}");
    }
}