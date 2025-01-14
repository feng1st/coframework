package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.common.log.util.LogUtils;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void apiExceptionCustomErrorMessage() {
        Result<?> result = loggingPluginTestService.apiExceptionCustomErrorMessage(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.apiExceptionCustomErrorMessage\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        Assertions.assertEquals("Custom error message", result.getErrorMessage());
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
    public void nonApiError() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.nonApiError(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.nonApiError\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
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
    public void loggingApiError() {
        loggingPluginTestService.loggingApiError(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingApiError\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void loggingApiErrorCritical() {
        loggingPluginTestService.loggingApiErrorCritical(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingApiErrorCritical\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void loggingInvalidState() {
        loggingPluginTestService.loggingInvalidState(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingInvalidState\",\"success\":false,\"code\":\"INVALID_STATE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void loggingServiceUnavailable() {
        loggingPluginTestService.loggingServiceUnavailable(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingServiceUnavailable\",\"success\":false,\"code\":\"SERVICE_UNAVAILABLE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void loggingExternalSysError() {
        loggingPluginTestService.loggingExternalSysError(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingExternalSysError\",\"success\":false,\"code\":\"EXTERNAL_SYS_ERROR\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
    }

    @Test
    public void loggingIllegalArgumentException() {
        loggingPluginTestService.loggingIllegalArgumentException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.WARN,
                IllegalArgumentException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestService.loggingIllegalArgumentException\",\"success\":false,\"code\":\"INVALID_ARGS\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalArgumentException: Message\"}");
    }

    @Test
    public void loggingRootException() {
        loggingPluginTestService.loggingRootException(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestService.loggingRootException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Root message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
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
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingPage\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":{\"data\":[1,2,3],\"pageIndex\":1,\"pageSize\":20,\"totalCount\":null,\"hasMore\":null}}");
    }

    @Test
    public void loggingPageResult() {
        loggingPluginTestService.loggingPageResult(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingPageResult\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":{\"data\":[1,2,3],\"pageIndex\":1,\"pageSize\":20,\"totalCount\":null,\"hasMore\":null}}");
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
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.invalidSpEL\",\"message\":\"(SPEL_ERROR: EL1007E: Property or field 'INVALID_EXP' cannot be found on null)\",\"elapsed\":0}");
    }

    @Test
    public void loggingNoJson() {
        LogUtils.logAsJson = false;
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestService.loggingException(1, 2));
        LogUtils.logAsJson = true;

        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestService",
                Level.ERROR,
                IllegalStateException.class,
                "{level=ERROR, method=LoggingPluginTestService.loggingException, success=false, code=IllegalStateException, message=Message, elapsed=0, args={param1=1, param2=2}, exception=java.lang.IllegalStateException: Message}");
    }

    @Test
    public void loggingMalformed() {
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestExceptionParam(), 2);
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestExceptionParam(), new int[]{1, 2, 3});
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Integer[]{1, 2, 3});
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Object[]{1, 2, 3});
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestEmptyParam(), 2);
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestSelfRefParam(), 2);
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestWithDateParam(), 2);
        loggingPluginTestService.loggingSuccess(new LoggingPluginTestWithDurationParam(), 2);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_STRING_ERROR)\",\"param2\":2},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_STRING_ERROR)\",\"param2\":[1,2,3]},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_STRING_ERROR)\",\"param2\":[1,2,3]},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_STRING_ERROR)\",\"param2\":[1,2,3]},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":{},\"param2\":2},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":{\"self\":null},\"param2\":2},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":{\"date\":\"1970-01-01T00:00:00.000+00:00\"},\"param2\":2},\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"LoggingPluginTestWithDurationParam(duration=PT1H)\",\"param2\":2},\"result\":\"data\"}");
    }
}