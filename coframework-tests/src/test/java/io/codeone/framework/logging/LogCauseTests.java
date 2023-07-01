package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.domain.service.TestLogCauseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LogCauseTests extends BaseLogTests {

    @Resource
    private TestLogCauseService testLogCauseService;

    @Test
    void testApiError() {
        try {
            testLogCauseService.apiError();
        } catch (Exception ignored) {
        }

        assertLog(TestLogCauseService.class.getName(), Level.ERROR,
                RuntimeException.class,
                // Cause found is MyException(CommonErrors.INVALID_PARAM, ...).
                "||level=>ERROR||method=>TestLogCauseService.apiError||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0||error=>java.lang.RuntimeException: io.codeone.framework.logging.domain.exception.MyException: Invalid param");
    }

    @Test
    void testInvalidParam() {
        try {
            testLogCauseService.invalidParam();
        } catch (Exception ignored) {
        }

        assertLog(TestLogCauseService.class.getName(), Level.ERROR,
                RuntimeException.class,
                // Cause found is IllegalArgumentException("Negative", ...).
                "||level=>ERROR||method=>TestLogCauseService.invalidParam||success=>false||code=>INVALID_PARAM||message=>Negative||elapsed=>0||error=>java.lang.RuntimeException: java.lang.IllegalArgumentException: Negative");
    }

    @Test
    void testOtherException() {
        try {
            testLogCauseService.otherException();
        } catch (Exception ignored) {
        }

        assertLog(TestLogCauseService.class.getName(), Level.ERROR,
                RuntimeException.class,
                // Cause found is Exception("Deeply sorry").
                "||level=>ERROR||method=>TestLogCauseService.otherException||success=>false||code=>Exception||message=>Deeply sorry||elapsed=>0||error=>java.lang.RuntimeException: java.lang.Exception: java.lang.Exception: java.lang.Exception: Deeply sorry");
    }
}
