package io.codeone.framework.logging.plugin;

import ch.qos.logback.classic.Level;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.shared.BaseLoggingTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LoggingPluginTestForLoggingCustom extends BaseLoggingTest {

    @Autowired
    private LoggingPluginTestLoggingService loggingPluginTestLoggingService;

    private static String originalFormat;

    @BeforeAll
    static void beforeAll() {
        originalFormat = LogFormatUtils.format;
        LogFormatUtils.format = LogFormat.CUSTOM;
    }

    @AfterAll
    static void afterAll() {
        LogFormatUtils.format = originalFormat;
    }

    @Test
    public void loggingNoJson() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> loggingPluginTestLoggingService.loggingException(1, 2));
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.ERROR,
                IllegalStateException.class,
                "level=>ERROR||method=>LoggingPluginTestLoggingService.loggingException||success=>false||code=>IllegalStateException||message=>Message||elapsed=>0||args.param1=>1||args.param2=>2||exception=>\"java.lang.IllegalStateException: Message\"");
    }

    @Test
    public void loggingMultiLinesStringNoJson() {
        loggingPluginTestLoggingService.loggingMultiLinesString(1, 2);
        assertLog("io.codeone.framework.logging.plugin.LoggingPluginTestLoggingService",
                Level.INFO,
                null,
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingMultiLinesString||elapsed=>0||args.param1=>1||args.param2=>2||result=>\"Line 1\\nLine 2\"");
    }

    @Test
    public void loggingMalformedExceptionNullNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), null);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>null||result=>data");
    }

    @Test
    public void loggingMalformedExceptionStringNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), "2");
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedExceptionMapNoJson() {
        Map<Object, Object> map = new HashMap<>();
        map.put("2", 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"{\\\"2\\\":2}\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionMapSelfRefNoJson() {
        Map<Object, Object> map = new HashMap<>();
        map.put("2", map);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), map);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.util.HashMap)\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionCollectionNoJson() {
        List<Object> list = new ArrayList<>();
        list.add("2");
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"[\\\"2\\\"]\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionCollectionSelfRefNoJson() {
        List<Object> list = new ArrayList<>();
        list.add(list);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), list);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.util.ArrayList)\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionPrimitiveNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), 2);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>2||result=>data");
    }

    @Test
    public void loggingMalformedExceptionPrimitiveArrayNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new int[]{1, 2});
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionWrapperArrayNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Integer[]{1, 2});
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionObjectArrayNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new Object[]{1, 2});
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[1,2]||result=>data");
    }

    @Test
    public void loggingMalformedExceptionObjectArraySelfRefNoJson() {
        Object[] array = new Object[3];
        array[0] = 1;
        array[1] = 2;
        array[2] = array;
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), array);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>\"(TO_JSON_STRING_ERROR: java.lang.Object[])\"||result=>data");
    }

    @Test
    public void loggingMalformedExceptionExceptionMapNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionMapParam());
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>{}||result=>data");
    }

    @Test
    public void loggingMalformedExceptionExceptionCollectionNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestExceptionParam(), new LoggingPluginTestExceptionCollectionParam());
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestExceptionParam)\"||args.param2=>[]||result=>data");
    }

    @Test
    public void loggingMalformedNoJson() {
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestEmptyParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestSelfRefParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDateParam(), 2);
        loggingPluginTestLoggingService.loggingSuccess(new LoggingPluginTestWithDurationParam(), 2);
        assertLogs("level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>{}||args.param2=>2||result=>data",
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"{\\\"self\\\":null}\"||args.param2=>2||result=>data",
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"{\\\"date\\\":\\\"1970-01-01T00:00:00.000+00:00\\\"}\"||args.param2=>2||result=>data",
                "level=>INFO||method=>LoggingPluginTestLoggingService.loggingSuccess||success=>true||elapsed=>0||args.param1=>\"(TO_JSON_STRING_ERROR: io.codeone.framework.logging.plugin.LoggingPluginTestWithDurationParam)\"||args.param2=>2||result=>data");
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
}