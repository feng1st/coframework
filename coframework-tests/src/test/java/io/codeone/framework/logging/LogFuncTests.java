package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.domain.exception.MyException;
import io.codeone.framework.logging.domain.service.TestLogFuncService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LogFuncTests extends BaseLogTests {

    @Resource
    private TestLogFuncService testLogFuncService;

    @Test
    void testCustomMessage() {
        testLogFuncService.customMessage();

        assertLog(TestLogFuncService.class.getName(), Level.ERROR, null,
                // Logged message is not from API.errorMessage.
                "||level=>ERROR||method=>TestLogFuncService.customMessage||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0");
    }

    @Test
    void testStackTrace() {
        testLogFuncService.stackTrace();

        assertLog(TestLogFuncService.class.getName(), Level.ERROR,
                // Has no stack trace since it's not a SysError.
                MyException.class,
                "||level=>ERROR||method=>TestLogFuncService.stackTrace||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0||error=>io.codeone.framework.logging.domain.exception.MyException: Invalid param");
    }
}
