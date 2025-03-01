package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.Assertions;
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
class LoggingPluginTest extends BaseLoggingTest {

    // --- @Logging ---

    @Autowired
    private LoggingPluginTestLoggingService loggingPluginTestLoggingService;

    @Test
    public void loggingSuccess() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=1 args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>1||args.param2=>2||result=>data");
    }

    @Test
    public void loggingFailure() {
        test(() -> {
            loggingPluginTestLoggingService.loggingFailure(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=WARN method=LoggingPluginTestLoggingService.loggingFailure success=false code=CODE message=Message elapsed=0 args.param1=1 args.param2=2");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=>WARN||method=>LoggingPluginTestLoggingService.loggingFailure||success=>false||code=>CODE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2");
    }

    @Test
    public void loggingException() {
        test(() -> {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> loggingPluginTestLoggingService.loggingException(1, 2));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestLoggingService.loggingException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, IllegalStateException.class,
                "level=ERROR method=LoggingPluginTestLoggingService.loggingException success=false code=IllegalStateException message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestLoggingService.loggingException||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void loggingNoState() {
        test(() -> {
            loggingPluginTestLoggingService.loggingNoState(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingNoState\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":null}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingNoState elapsed=0 args.param1=1 args.param2=2 result=null");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingNoState||elapsed=>0||args.param1=>1||args.param2=>2||result=>null");
    }

    @Test
    public void loggingPage() {
        test(() -> {
            loggingPluginTestLoggingService.loggingPage(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingPage\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingPage elapsed=0 args.param1=1 args.param2=2 result=\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingPage||elapsed=>0||args.param1=>1||args.param2=>2||result=>\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"");
    }

    @Test
    public void loggingPageResult() {
        test(() -> {
            loggingPluginTestLoggingService.loggingPageResult(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingPageResult\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingPageResult success=true elapsed=0 args.param1=1 args.param2=2 result=\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingPageResult||success=>true||elapsed=>0||args.param1=>1||args.param2=>2||result=>\"{\\\"data\\\":[1,2,3],\\\"pageIndex\\\":1,\\\"pageSize\\\":20,\\\"totalCount\\\":null,\\\"hasMore\\\":null}\"");
    }

    @Test
    public void loggingMultiLinesString() {
        test(() -> {
            loggingPluginTestLoggingService.loggingMultiLinesString(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingMultiLinesString\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"Line 1\\nLine 2\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingMultiLinesString elapsed=0 args.param1=1 args.param2=2 result=\"Line 1\\nLine 2\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingMultiLinesString||elapsed=>0||args.param1=>1||args.param2=>2||result=>\"Line 1\\nLine 2\"");
    }

    @Test
    public void loggingCustomLogger() {
        test(() -> {
            loggingPluginTestLoggingService.loggingCustomLogger(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "customLogger", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingCustomLogger\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "customLogger", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingCustomLogger success=true elapsed=0 args.param1=1 args.param2=2 result=data");
        assertLog(2, 3, "customLogger", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingCustomLogger||success=>true||elapsed=>0||args.param1=>1||args.param2=>2||result=>data");
    }

    @Test
    public void loggingNoDetailsSuccess() {
        test(() -> {
            loggingPluginTestLoggingService.loggingNoDetailsSuccess(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsSuccess\",\"success\":true,\"elapsed\":0}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingNoDetailsSuccess success=true elapsed=0");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingNoDetailsSuccess||success=>true||elapsed=>0");
    }

    @Test
    public void loggingNoDetailsFailure() {
        test(() -> {
            loggingPluginTestLoggingService.loggingNoDetailsFailure(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=WARN method=LoggingPluginTestLoggingService.loggingNoDetailsFailure success=false code=CODE message=Message elapsed=0");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=>WARN||method=>LoggingPluginTestLoggingService.loggingNoDetailsFailure||success=>false||code=>CODE||message=>Message||elapsed=>0");
    }

    @Test
    public void loggingNoDetailsException() {
        test(() -> {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> loggingPluginTestLoggingService.loggingNoDetailsException(1, 2));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, null,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestLoggingService.loggingNoDetailsException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, null,
                "level=ERROR method=LoggingPluginTestLoggingService.loggingNoDetailsException success=false code=IllegalStateException message=Message elapsed=0 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.ERROR, null,
                "level=>ERROR||method=>LoggingPluginTestLoggingService.loggingNoDetailsException||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void loggingSpEL() {
        test(() -> {
            Map<String, Object> param = new HashMap<>();
            param.put("userId", 10000L);
            loggingPluginTestLoggingService.loggingSpEL(param, null);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestLoggingService.loggingSpEL\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"userId\":10000,\"extra\":null},\"result\":\"{\\\"code\\\":\\\"CODE\\\",\\\"success\\\":false,\\\"message\\\":\\\"Message\\\"}\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=WARN method=LoggingPluginTestLoggingService.loggingSpEL success=false code=CODE message=Message elapsed=0 args.userId=10000 args.extra=null result=\"{\\\"code\\\":\\\"CODE\\\",\\\"success\\\":false,\\\"message\\\":\\\"Message\\\"}\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.WARN, null,
                "level=>WARN||method=>LoggingPluginTestLoggingService.loggingSpEL||success=>false||code=>CODE||message=>Message||elapsed=>0||args.userId=>10000||args.extra=>null||result=>\"{\\\"code\\\":\\\"CODE\\\",\\\"success\\\":false,\\\"message\\\":\\\"Message\\\"}\"");
    }

    @Test
    public void loggingSpELNoParam() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSpELNoParam();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSpELNoParam\",\"success\":true,\"elapsed\":0,\"args\":{},\"result\":\"{\\\"success\\\":true}\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSpELNoParam success=true elapsed=0 result=\"{\\\"success\\\":true}\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSpELNoParam||success=>true||elapsed=>0||result=>\"{\\\"success\\\":true}\"");
    }

    @Test
    public void loggingInvalidSpEL() {
        test(() -> {
            loggingPluginTestLoggingService.loggingInvalidSpEL();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingInvalidSpEL\",\"message\":\"(SPEL_ERROR: EL1007E: Property or field 'INVALID_EXP' cannot be found on null)\",\"elapsed\":0,\"args\":{}}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingInvalidSpEL message=\"(SPEL_ERROR: EL1007E: Property or field 'INVALID_EXP' cannot be found on null)\" elapsed=0");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingInvalidSpEL||message=>\"(SPEL_ERROR: EL1007E: Property or field 'INVALID_EXP' cannot be found on null)\"||elapsed=>0");
    }

    @Test
    public void loggingMalformedException() {
        test(() -> {
            // Will throw a malformed exception, can not be caught using Assertions.assertThrows
            try {
                loggingPluginTestLoggingService.loggingMalformedException();
            } catch (Exception ignored) {
            }
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "logging", Level.ERROR, IllegalStateException.class,
                "Failed to log invocation of method \"io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService.loggingMalformedException()\".");
        assertLog(1, 3, "logging", Level.ERROR, IllegalStateException.class,
                "Failed to log invocation of method \"io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService.loggingMalformedException()\".");
        assertLog(2, 3, "logging", Level.ERROR, IllegalStateException.class,
                "Failed to log invocation of method \"io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService.loggingMalformedException()\".");
    }

    @Test
    void loggingJsonNotLoaded() {
        try (MockedStatic<JsonUtils> mockedJsonUtils = Mockito.mockStatic(JsonUtils.class)) {
            mockedJsonUtils.when(JsonUtils::isLoaded).thenReturn(false);

            test(() -> {
                loggingPluginTestLoggingService.loggingSuccess(1, 2);
            }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);

            assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                    "(JSON formatting disabled: Jackson not found. Add jackson-databind dependency or set coframework.log.format in application.properties to non-json format)");
            assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                    "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=1 args.param2=2 result=data");
            assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                    "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>1||args.param2=>2||result=>data");
        }
    }

    @Test
    public void loggingMalformedExceptionNull() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), null);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":null},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=null result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>null||result=>data");
    }

    @Test
    public void loggingMalformedExceptionString() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), "2");
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"2\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedExceptionMap() {
        test(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("2", 2);
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"{\\\"2\\\":2}\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=\"{\\\"2\\\":2}\" result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"{\\\"2\\\":2}\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionMapSelfRef() {
        test(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("2", map);
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"(TO_JSON_STRING_ERROR: java.util.HashMap)\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=\"(TO_JSON_STRING_ERROR: java.util.HashMap)\" result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.util.HashMap)\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionCollection() {
        test(() -> {
            List<Object> list = new ArrayList<>();
            list.add("2");
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"[\\\"2\\\"]\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=\"[\\\"2\\\"]\" result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"[\\\"2\\\"]\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionCollectionSelfRef() {
        test(() -> {
            List<Object> list = new ArrayList<>();
            list.add(list);
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"(TO_JSON_STRING_ERROR: java.util.ArrayList)\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=\"(TO_JSON_STRING_ERROR: java.util.ArrayList)\" result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.util.ArrayList)\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionPrimitive() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedExceptionPrimitiveArray() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new int[]{1, 2});
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"[1,2]\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=[1,2] result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionWrapperArray() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Integer[]{1, 2});
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"[1,2]\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=[1,2] result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionObjectArray() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Object[]{1, 2});
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"[1,2]\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=[1,2] result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionObjectArraySelfRef() {
        test(() -> {
            Object[] array = new Object[3];
            array[0] = 1;
            array[1] = 2;
            array[2] = array;
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), array);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"(TO_JSON_STRING_ERROR: java.lang.Object[])\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=\"(TO_JSON_STRING_ERROR: java.lang.Object[])\" result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.lang.Object[])\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionExceptionMap() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionMapParam());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"{}\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2={} result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>{}||result=>data");
    }

    @Test
    public void loggingMalformedExceptionExceptionCollection() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionCollectionParam());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":\"[]\"},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2=[] result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[]||result=>data");
    }

    @Test
    public void loggingMalformedCircularLogMap() {
        test(() -> {
            LogMap<Object, Object> map = new LogMap<>();
            map.put("2", 2);
            map.put("self", map);
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\",\"param2\":{\"2\":2,\"self\":null}},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\" args.param2.2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2.2=>2||result=>data");
    }

    @Test
    public void loggingMalformedJacksonEmptyBean() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestEmptyParam(), 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"{}\",\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1={} args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>{}||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedJacksonSelfRef() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestSelfRefParam(), 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"{\\\"self\\\":null}\",\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"{\\\"self\\\":null}\" args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"{\\\"self\\\":null}\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedJacksonDate() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDateParam(), 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"{\\\"date\\\":\\\"1970-01-01T00:00:00.000+00:00\\\"}\",\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"{\\\"date\\\":\\\"1970-01-01T00:00:00.000+00:00\\\"}\" args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"{\\\"date\\\":\\\"1970-01-01T00:00:00.000+00:00\\\"}\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedJacksonDuration() {
        test(() -> {
            loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDurationParam(), 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestLoggingService.loggingSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestWithDurationParam)\",\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestLoggingService.loggingSuccess success=true elapsed=0 args.param1=\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestWithDurationParam)\" args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestWithDurationParam)\"||args.param2=>2||result=>data");
    }

    // --- @API ---

    @Autowired
    private LoggingPluginTestApiService loggingPluginTestApiService;

    @Test
    public void apiSuccess() {
        test(() -> {
            loggingPluginTestApiService.apiSuccess(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestApiService.apiSuccess\",\"success\":true,\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":\"data\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestApiService.apiSuccess success=true elapsed=0 args.param1=1 args.param2=2 result=data");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestApiService.apiSuccess||success=>true||elapsed=>0||args.param1=>1||args.param2=>2||result=>data");
    }

    @Test
    public void apiFailure() {
        test(() -> {
            loggingPluginTestApiService.apiFailure(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, null,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiFailure\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2}}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, null,
                "level=WARN method=LoggingPluginTestApiService.apiFailure success=false code=CODE message=Message elapsed=0 args.param1=1 args.param2=2");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, null,
                "level=>WARN||method=>LoggingPluginTestApiService.apiFailure||success=>false||code=>CODE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2");
    }

    @Test
    public void apiException() {
        test(() -> {
            loggingPluginTestApiService.apiException(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiException\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiException success=false code=IllegalStateException message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiException||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void apiExceptionCustomErrorMessage() {
        test(() -> {
            Result<?> result = loggingPluginTestApiService.apiExceptionCustomErrorMessage(1, 2);
            Assertions.assertEquals("Custom error message", result.getErrorMessage());
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiExceptionCustomErrorMessage\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiExceptionCustomErrorMessage success=false code=IllegalStateException message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiExceptionCustomErrorMessage||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void apiNonApiResult() {
        test(() -> {
            loggingPluginTestApiService.apiNonApiResult(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LoggingPluginTestApiService.apiNonApiResult\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"result\":null}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "level=INFO method=LoggingPluginTestApiService.apiNonApiResult elapsed=0 args.param1=1 args.param2=2 result=null");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.INFO, null,
                "level=>INFO||method=>LoggingPluginTestApiService.apiNonApiResult||elapsed=>0||args.param1=>1||args.param2=>2||result=>null");
    }

    @Test
    public void apiNonApiError() {
        test(() -> {
            Assertions.assertThrows(IllegalStateException.class,
                    () -> loggingPluginTestApiService.apiNonApiError(1, 2));
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiNonApiError\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiNonApiError success=false code=IllegalStateException message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiNonApiError||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void apiErrorNonCritical() {
        test(() -> {
            loggingPluginTestApiService.apiErrorNonCritical(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorNonCritical\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=WARN method=LoggingPluginTestApiService.apiErrorNonCritical success=false code=CODE message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"io.codeone.framework.api.exception.ApiException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=>WARN||method=>LoggingPluginTestApiService.apiErrorNonCritical||success=>false||code=>CODE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"io.codeone.framework.api.exception.ApiException: Message\"");
    }

    @Test
    public void apiErrorCritical() {
        test(() -> {
            loggingPluginTestApiService.apiErrorCritical(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorCritical\",\"success\":false,\"code\":\"CODE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiErrorCritical success=false code=CODE message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"io.codeone.framework.api.exception.ApiException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiErrorCritical||success=>false||code=>CODE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"io.codeone.framework.api.exception.ApiException: Message\"");
    }

    @Test
    public void apiErrorInvalidState() {
        test(() -> {
            loggingPluginTestApiService.apiErrorInvalidState(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorInvalidState\",\"success\":false,\"code\":\"INVALID_STATE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=WARN method=LoggingPluginTestApiService.apiErrorInvalidState success=false code=INVALID_STATE message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"io.codeone.framework.api.exception.ApiException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=>WARN||method=>LoggingPluginTestApiService.apiErrorInvalidState||success=>false||code=>INVALID_STATE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"io.codeone.framework.api.exception.ApiException: Message\"");
    }

    @Test
    public void apiErrorServiceUnavailable() {
        test(() -> {
            loggingPluginTestApiService.apiErrorServiceUnavailable(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorServiceUnavailable\",\"success\":false,\"code\":\"SERVICE_UNAVAILABLE\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=WARN method=LoggingPluginTestApiService.apiErrorServiceUnavailable success=false code=SERVICE_UNAVAILABLE message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"io.codeone.framework.api.exception.ApiException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, ApiException.class,
                "level=>WARN||method=>LoggingPluginTestApiService.apiErrorServiceUnavailable||success=>false||code=>SERVICE_UNAVAILABLE||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"io.codeone.framework.api.exception.ApiException: Message\"");
    }

    @Test
    public void apiErrorExternalSysError() {
        test(() -> {
            loggingPluginTestApiService.apiErrorExternalSysError(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorExternalSysError\",\"success\":false,\"code\":\"EXTERNAL_SYS_ERROR\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"io.codeone.framework.api.exception.ApiException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiErrorExternalSysError success=false code=EXTERNAL_SYS_ERROR message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"io.codeone.framework.api.exception.ApiException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, ApiException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiErrorExternalSysError||success=>false||code=>EXTERNAL_SYS_ERROR||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"io.codeone.framework.api.exception.ApiException: Message\"");
    }

    @Test
    public void apiErrorIllegalArgumentException() {
        test(() -> {
            loggingPluginTestApiService.apiErrorIllegalArgumentException(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, IllegalArgumentException.class,
                "{\"level\":\"WARN\",\"method\":\"LoggingPluginTestApiService.apiErrorIllegalArgumentException\",\"success\":false,\"code\":\"INVALID_ARGS\",\"message\":\"Message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalArgumentException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, IllegalArgumentException.class,
                "level=WARN method=LoggingPluginTestApiService.apiErrorIllegalArgumentException success=false code=INVALID_ARGS message=Message elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalArgumentException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.WARN, IllegalArgumentException.class,
                "level=>WARN||method=>LoggingPluginTestApiService.apiErrorIllegalArgumentException||success=>false||code=>INVALID_ARGS||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalArgumentException: Message\"");
    }

    @Test
    public void apiErrorRootCause() {
        test(() -> {
            loggingPluginTestApiService.apiErrorRootCause(1, 2);
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "{\"level\":\"ERROR\",\"method\":\"LoggingPluginTestApiService.apiErrorRootCause\",\"success\":false,\"code\":\"IllegalStateException\",\"message\":\"Root message\",\"elapsed\":0,\"args\":{\"param1\":1,\"param2\":2},\"exception\":\"java.lang.IllegalStateException: Message\"}");
        assertLog(1, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=ERROR method=LoggingPluginTestApiService.apiErrorRootCause success=false code=IllegalStateException message=\"Root message\" elapsed=0 args.param1=1 args.param2=2 exception=\"java.lang.IllegalStateException: Message\"");
        assertLog(2, 3, "io.codeone.framework.logging.plugin.LoggingPluginTestApiService", Level.ERROR, IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestApiService.apiErrorRootCause||success=>false||code=>IllegalStateException||message=>\"Root message\"||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }
}