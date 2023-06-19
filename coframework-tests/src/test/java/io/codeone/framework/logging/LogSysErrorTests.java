package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.exception.SysException;
import io.codeone.framework.logging.domain.service.TestLogSysErrorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LogSysErrorTests extends BaseLogTests {

    @Resource
    private TestLogSysErrorService testLogSysErrorService;

    @Test
    void testSysError() {
        testLogSysErrorService.sysError();

        assertLog(TestLogSysErrorService.class.getName(),
                // The level of a SysError is ERROR.
                Level.ERROR,
                null,
                "||level=>ERROR||method=>TestLogSysErrorService.sysError||success=>false||code=>SYS_ERROR||message=>System error||elapsed=>0");
    }

    @Test
    void testCustomSysError() {
        testLogSysErrorService.customSysError();

        assertLog(TestLogSysErrorService.class.getName(),
                // The level of a SysError is ERROR.
                Level.ERROR,
                null,
                "||level=>ERROR||method=>TestLogSysErrorService.customSysError||success=>false||code=>SYS_ERROR||message=>System error||elapsed=>0");
    }

    @Test
    void testNonSysError() {
        testLogSysErrorService.nonSysError();

        assertLog(TestLogSysErrorService.class.getName(),
                // The level is WARN since it's not a SysError.
                Level.WARN,
                null,
                "||level=>WARN||method=>TestLogSysErrorService.nonSysError||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0");
    }

    @Test
    void testLoggedMessageForSysError() {
        testLogSysErrorService.loggedMessageForSysError();

        assertLog(TestLogSysErrorService.class.getName(), Level.ERROR, null,
                // Logged message is not from API.sysErrorMessage.
                "||level=>ERROR||method=>TestLogSysErrorService.loggedMessageForSysError||success=>false||code=>SYS_ERROR||message=>System error||elapsed=>0");
    }

    @Test
    void testStackTraceForSysError() {
        testLogSysErrorService.stackTraceForSysError();

        assertLog(TestLogSysErrorService.class.getName(), Level.ERROR,
                // Has the stack trace.
                SysException.class,
                "||level=>ERROR||method=>TestLogSysErrorService.stackTraceForSysError||success=>false||code=>SYS_ERROR||message=>System error||elapsed=>0||error=>io.codeone.framework.exception.SysException: System error");
    }

    @Test
    void testStackTraceForNonSysError() {
        testLogSysErrorService.stackTraceForNonSysError();

        assertLog(TestLogSysErrorService.class.getName(), Level.WARN,
                // Has no stack trace since it's not a SysError.
                null,
                "||level=>WARN||method=>TestLogSysErrorService.stackTraceForNonSysError||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0||error=>io.codeone.framework.logging.domain.exception.MyException: Invalid param");
    }
}
