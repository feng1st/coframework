package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.shared.BaseLoggingTest;
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
        loggingPluginTestService.apiSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.apiSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
    }

    @Test
    public void apiFailure() {
        loggingPluginTestService.apiFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.apiFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
    }

    @Test
    public void apiException() {
        loggingPluginTestService.apiException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.apiException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void nonApiResult() {
        loggingPluginTestService.nonApiResult(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.nonApiResult\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
    }

    @Test
    public void nonApiException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.nonApiException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.nonApiException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void loggingSuccess() {
        loggingPluginTestService.loggingSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
    }

    @Test
    public void loggingFailure() {
        loggingPluginTestService.loggingFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
    }

    @Test
    public void loggingException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.loggingException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void loggingNoState() {
        loggingPluginTestService.loggingNoState(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingNoState\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
    }

    @Test
    public void loggingPage() {
        loggingPluginTestService.loggingPage(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingPage\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":{\"data\":[1,2,3],\"pageIndex\":1,\"pageSize\":20,\"totalCount\":null,\"hasMore\":false,\"empty\":false}}");
    }

    @Test
    public void loggingPageResult() {
        loggingPluginTestService.loggingPageResult(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingPageResult\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":{\"data\":[1,2,3],\"pageIndex\":1,\"pageSize\":20,\"totalCount\":null,\"hasMore\":false,\"empty\":false}}");
    }

    @Test
    public void loggingCustomLogger() {
        loggingPluginTestService.loggingCustomLogger(1, 2);
        assertLog("customLogger",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingCustomLogger\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
    }

    @Test
    public void loggingNoDetailsSuccess() {
        loggingPluginTestService.loggingNoDetailsSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingNoDetailsSuccess\",\"success\":true,\"elapsed\":0}");
    }

    @Test
    public void loggingNoDetailsFailure() {
        loggingPluginTestService.loggingNoDetailsFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingNoDetailsFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0}");
    }

    @Test
    public void loggingNoDetailsException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.loggingNoDetailsException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                null,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingNoDetailsException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void loggingSpEL() {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", 10000L);
        loggingPluginTestService.loggingSpEL(param, null);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingSpEL\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"userId\":10000,\"extra\":null},\"result\":{\"code\":\"CODE\",\"success\":false,\"message\":\"Message\"}}");
    }

    @Test
    public void loggingSpELNoParam() {
        loggingPluginTestService.loggingSpELNoParam();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSpELNoParam\",\"success\":true,\"elapsed\":0,\"result\":{\"success\":true}}");
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