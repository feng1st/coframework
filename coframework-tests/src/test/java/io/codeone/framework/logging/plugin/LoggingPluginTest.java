package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.BaseLoggingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.SpelEvaluationException;

import java.util.HashMap;
import java.util.Map;

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
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.apiSuccess\",\"success\":true,\"elapsed\":0,\"result\":\"data\"}");
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
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"result\":\"data\"}");
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
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.loggingException());
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Exception\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Exception\"}");
    }

    @Test
    public void loggingDefault() {
        loggingPluginTestService.loggingDefault(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingDefault\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
    }

    @Test
    public void loggingCustom() {
        loggingPluginTestService.loggingCustom(1, 2);
        assertLog("customLogger",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingCustom\",\"success\":true,\"elapsed\":0}");
    }

    @Test
    public void loggingSpEL() {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", 10000L);
        loggingPluginTestService.loggingSpEL(param);
        assertLog("customLogger",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingSpEL\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"userId\":10000},\"result\":{\"code\":\"CODE\",\"message\":\"Message\",\"userId\":10000,\"success\":false}}");
    }

    @Test
    public void loggingSpELNoParam() {
        loggingPluginTestService.loggingSpELNoParam();
        assertLog("customLogger",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSpELNoParam\",\"success\":true,\"elapsed\":0,\"result\":{\"success\":true}}");
    }

    @Test
    public void loggingSpELException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.loggingSpELException());
        assertLog("customLogger",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingSpELException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Exception\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Exception\"}");
    }

    @Test
    public void invalidSpEL() {
        loggingPluginTestService.invalidSpEL();
        assertLog("logging",
                Level.ERROR,
                SpelEvaluationException.class,
                "Error logging invocation of 'public void io.codeone.framework.logging.plugin.LoggingPluginTestService.invalidSpEL()'");
    }
}