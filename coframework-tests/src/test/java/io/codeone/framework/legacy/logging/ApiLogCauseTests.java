package io.codeone.framework.legacy.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.legacy.logging.domain.service.TestApiLogCauseService;
import io.codeone.framework.logging.BaseLoggingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiLogCauseTests extends BaseLoggingTest {

    @Autowired
    private TestApiLogCauseService testApiLogCauseService;

    @Test
    void testApiError() {
        testApiLogCauseService.apiError();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, RuntimeException.class,
                // Cause found is MyException(CommonErrors.INVALID_ARGS, ...).
                "{level=ERROR, method=TestApiLogCauseService.apiError, success=false, code=INVALID_ARGS, message=Invalid arguments, elapsed=0, exception=java.lang.RuntimeException: io.codeone.framework.api.logging.domain.exception.MyException: Invalid arguments}");
    }

    @Test
    void testInvalidParam() {
        testApiLogCauseService.invalidParam();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, RuntimeException.class,
                // Cause found is IllegalArgumentException("Negative", ...).
                "{level=ERROR, method=TestApiLogCauseService.invalidParam, success=false, code=INVALID_ARGS, message=Negative, elapsed=0, exception=java.lang.RuntimeException: java.lang.IllegalArgumentException: Negative}");
    }

    @Test
    void testOtherException() {
        testApiLogCauseService.otherException();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, RuntimeException.class,
                // Cause found is Exception("Deeply sorry").
                "{level=ERROR, method=TestApiLogCauseService.otherException, success=false, code=Exception, message=Deeply sorry, elapsed=0, exception=java.lang.RuntimeException: java.lang.Exception: java.lang.Exception: java.lang.Exception: Deeply sorry}");
    }
}
