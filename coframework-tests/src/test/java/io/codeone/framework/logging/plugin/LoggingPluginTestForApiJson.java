package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoggingPluginTestForApiJson extends BaseLoggingTest {

    @Autowired
    private LoggingPluginTestApiService loggingPluginTestApiService;

    private static String originalFormat;

    @BeforeAll
    static void beforeAll() {
        originalFormat = LogFormatUtils.format;
        LogFormatUtils.format = LogFormat.JSON;
    }

    @AfterAll
    static void afterAll() {
        LogFormatUtils.format = originalFormat;
    }

    @Test
    public void apiSuccess() {
        loggingPluginTestApiService.apiSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestApiService.apiSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"data\"}");
    }

    @Test
    public void apiFailure() {
        loggingPluginTestApiService.apiFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2}");
    }

    @Test
    public void apiException() {
        loggingPluginTestApiService.apiException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void apiExceptionCustomErrorMessage() {
        Result<?> result = loggingPluginTestApiService.apiExceptionCustomErrorMessage(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiExceptionCustomErrorMessage\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalStateException: Message\"}");
        Assertions.assertEquals("Custom error message", result.getErrorMessage());
    }

    @Test
    public void nonApiResult() {
        loggingPluginTestApiService.nonApiResult(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestApiService.nonApiResult\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":null}");
    }

    @Test
    public void nonApiError() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestApiService.nonApiError(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.nonApiError\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void apiErrorNonCritical() {
        loggingPluginTestApiService.apiErrorNonCritical(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorNonCritical\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void apiErrorCritical() {
        loggingPluginTestApiService.apiErrorCritical(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorCritical\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void apiErrorInvalidState() {
        loggingPluginTestApiService.apiErrorInvalidState(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorInvalidState\",\"success\":false,\"code\":\"INVALID_STATE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void apiErrorServiceUnavailable() {
        loggingPluginTestApiService.apiErrorServiceUnavailable(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorServiceUnavailable\",\"success\":false,\"code\":\"SERVICE_UNAVAILABLE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void apiErrorExternalSysError() {
        loggingPluginTestApiService.apiErrorExternalSysError(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorExternalSysError\",\"success\":false,\"code\":\"EXTERNAL_SYS_ERROR\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void apiErrorIllegalArgumentException() {
        loggingPluginTestApiService.apiErrorIllegalArgumentException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.WARN,
                IllegalArgumentException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorIllegalArgumentException\",\"success\":false,\"code\":\"INVALID_ARGS\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalArgumentException: Message\"}");
    }

    @Test
    public void apiErrorRootException() {
        loggingPluginTestApiService.apiErrorRootException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestApiService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorRootException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Root message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }
}