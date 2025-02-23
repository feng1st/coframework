package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LoggingPluginTestForLoggingJson extends BaseLoggingTest {

    @Autowired
    private LoggingPluginTestLoggingService loggingPluginTestLoggingService;

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
    public void loggingSuccess() {
        loggingPluginTestLoggingService.loggingSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"data\"}");
    }

    @Test
    public void loggingFailure() {
        loggingPluginTestLoggingService.loggingFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2}");
    }

    @Test
    public void loggingException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestLoggingService.loggingException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.ERROR,
                IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestLoggingService.loggingException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void loggingNoState() {
        loggingPluginTestLoggingService.loggingNoState(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingNoState\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":null}");
    }

    @Test
    public void loggingPage() {
        loggingPluginTestLoggingService.loggingPage(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingPage\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"}");
    }

    @Test
    public void loggingPageResult() {
        loggingPluginTestLoggingService.loggingPageResult(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingPageResult\",\"success\":true,\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"}");
    }

    @Test
    public void loggingMultiLinesString() {
        loggingPluginTestLoggingService.loggingMultiLinesString(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingMultiLinesString\",\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"Line 1\\nLine 2\"}");
    }

    @Test
    public void loggingCustomLogger() {
        loggingPluginTestLoggingService.loggingCustomLogger(1, 2);
        assertLog("customLogger",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingCustomLogger\",\"success\":true,\"elapsed\":0,\"args.param1\":1,\"args.param2\":2,\"result\":\"data\"}");
    }

    @Test
    public void loggingNoDetailsSuccess() {
        loggingPluginTestLoggingService.loggingNoDetailsSuccess(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsSuccess\",\"success\":true,\"elapsed\":0}");
    }

    @Test
    public void loggingNoDetailsFailure() {
        loggingPluginTestLoggingService.loggingNoDetailsFailure(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0}");
    }

    @Test
    public void loggingNoDetailsException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestLoggingService.loggingNoDetailsException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.ERROR,
                null,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Message\"}");
    }

    @Test
    public void loggingSpEL() {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", 10000L);
        loggingPluginTestLoggingService.loggingSpEL(param, null);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.WARN,
                null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingSpEL\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args.userId\":10000,\"args.extra\":null,\"result\":\"{\\\"code\\\":\\\"CODE\\\",\\\"success\\\":false,\\\"message\\\":\\\"Message\\\"}\"}");
    }

    @Test
    public void loggingSpELNoParam() {
        loggingPluginTestLoggingService.loggingSpELNoParam();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSpELNoParam\",\"success\":true,\"elapsed\":0,\"result\":\"{\\\"success\\\":true}\"}");
    }

    @Test
    public void invalidSpEL() {
        loggingPluginTestLoggingService.invalidSpEL();
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.invalidSpEL\",\"message\":\"(SPEL_ERROR: EL1007E: Property or field 'INVALID_EXP' cannot be found on null)\",\"elapsed\":0}");
    }

    @Test
    public void loggingMalformedExceptionNull() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), null);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":null,\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionString() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), "2");
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"2\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put("2", 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"{\\\"2\\\":2}\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionMapSelfRef() {
        Map<Object, Object> map = new HashMap<>();
        map.put("2", map);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"(TO_JSON_STRING_ERROR: java.util.HashMap)\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionCollection() {
        List<Object> list = new ArrayList<>();
        list.add("2");
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"[\\\"2\\\"]\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionCollectionSelfRef() {
        List<Object> list = new ArrayList<>();
        list.add(list);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"(TO_JSON_STRING_ERROR: java.util.ArrayList)\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionPrimitive() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), 2);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":2,\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionPrimitiveArray() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new int[]{1, 2});
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"[1,2]\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionWrapperArray() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Integer[]{1, 2});
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"[1,2]\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionObjectArray() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Object[]{1, 2});
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"[1,2]\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionObjectArraySelfRef() {
        Object[] array = new Object[3];
        array[0] = 1;
        array[1] = 2;
        array[2] = array;
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), array);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"(TO_JSON_STRING_ERROR: java.lang.Object[])\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionExceptionMap() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionMapParam());
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"{}\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformedExceptionExceptionCollection() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionCollectionParam());
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"args.param2\":\"[]\",\"result\":\"data\"}");
    }

    @Test
    public void loggingMalformed() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestEmptyParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestSelfRefParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDateParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDurationParam(), 2);
        assertLogs("{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"{}\",\"args.param2\":2,\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"{\\\"self\\\":null}\",\"args.param2\":2,\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"{\\\"date\\\":\\\"1970-01-01T00:00:00.000+00:00\\\"}\",\"args.param2\":2,\"result\":\"data\"}",
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args.param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestWithDurationParam)\",\"args.param2\":2,\"result\":\"data\"}");
    }

    @Test
    public void edgeMalformedException() {
        // Will throw a malformed exception, can not be caught using Assertions.assertThrows
        try {
            loggingPluginTestLoggingService.edgeMalformedException();
        } catch (Exception ignored) {
        }
        assertLog("logging",
                Level.ERROR,
                IllegalStateException.class,
                "Failed to log invocation of method \"io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService.edgeMalformedException()\".");
    }

    @Test
    void edgeJsonNotLoaded() {
        try (MockedStatic<JsonUtils> mockedJsonUtils = Mockito.mockStatic(JsonUtils.class)) {
            mockedJsonUtils.when(JsonUtils::isLoaded).thenReturn(false);

            loggingPluginTestLoggingService.loggingSuccess(1, 2);

            assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                    Level.INFO,
                    null,
                    "(JSON formatting disabled: Jackson not found. Add jackson-databind dependency or set coframework.log.format in application.properties to non-json format)");
        }
    }
}