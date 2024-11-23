package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.domain.service.TestLogCauseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogCauseTests extends BaseLogTests {

    @Autowired
    private TestLogCauseService testLogCauseService;

    @Test
    void testApiError() {
        try {
            testLogCauseService.apiError();
        } catch (Exception ignored) {
        }

        assertLog(TestLogCauseService.class.getName(), Level.ERROR,
                RuntimeException.class,
                // Cause found is MyException(CommonErrors.INVALID_ARGS, ...).
                "{level=ERROR, method=TestLogCauseService.apiError, success=false, code=INVALID_ARGS, message=Invalid arguments, elapsed=0, exception=java.lang.RuntimeException: io.codeone.framework.logging.domain.exception.MyException: Invalid arguments}");
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
                "{level=ERROR, method=TestLogCauseService.invalidParam, success=false, code=INVALID_ARGS, message=Negative, elapsed=0, exception=java.lang.RuntimeException: java.lang.IllegalArgumentException: Negative}");
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
                "{level=ERROR, method=TestLogCauseService.otherException, success=false, code=Exception, message=Deeply sorry, elapsed=0, exception=java.lang.RuntimeException: java.lang.Exception: java.lang.Exception: java.lang.Exception: Deeply sorry}");
    }
}
