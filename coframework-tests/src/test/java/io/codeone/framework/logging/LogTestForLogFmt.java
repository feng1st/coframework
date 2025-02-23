package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.shared.BaseLoggingTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
class LogTestForLogFmt extends BaseLoggingTest {

    private static String originalFormat;

    @BeforeAll
    static void beforeAll() {
        originalFormat = LogFormatUtils.format;
        LogFormatUtils.format = LogFormat.LOG_FMT;
    }

    @AfterAll
    static void afterAll() {
        LogFormatUtils.format = originalFormat;
    }

    @Test
    void logFmtUtils() {
        Log.newLog()
                .putContext(null, null)
                .putContext("", "")
                .putContext("中文", "中文")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "level=INFO method=anonymous ctx.null=null ctx.=\"\" ctx.__=中文");
    }
}