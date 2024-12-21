package io.codeone.framework.legacy.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.legacy.logging.domain.exception.MyException;
import io.codeone.framework.legacy.logging.domain.service.TestApiLogFuncService;
import io.codeone.framework.logging.BaseLoggingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiLogFuncTests extends BaseLoggingTest {

    @Autowired
    private TestApiLogFuncService testApiLogFuncService;

    @Test
    void testCustomMessage() {
        testApiLogFuncService.customMessage();

        assertLog(TestApiLogFuncService.class.getName(), Level.ERROR, MyException.class,
                // Logged message is not from API.errorMessage.
                "{level=ERROR, method=TestApiLogFuncService.customMessage, success=false, code=INVALID_ARGS, message=Invalid arguments, elapsed=0, exception=io.codeone.framework.api.logging.domain.exception.MyException: Invalid arguments}");
    }

    @Test
    void testStackTrace() {
        testApiLogFuncService.stackTrace();

        assertLog(TestApiLogFuncService.class.getName(), Level.ERROR,
                // Has no stack trace since it's not a SysError.
                MyException.class,
                "{level=ERROR, method=TestApiLogFuncService.stackTrace, success=false, code=INVALID_ARGS, message=Invalid arguments, elapsed=0, exception=io.codeone.framework.api.logging.domain.exception.MyException: Invalid arguments}");
    }
}
