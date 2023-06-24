package io.codeone.framework.api.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.logging.domain.exception.MyException;
import io.codeone.framework.api.logging.domain.service.TestApiLogFuncService;
import io.codeone.framework.logging.BaseLogTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiLogFuncTests extends BaseLogTests {

    @Resource
    private TestApiLogFuncService testApiLogFuncService;

    @Test
    void testCustomMessage() {
        testApiLogFuncService.customMessage();

        assertLog(TestApiLogFuncService.class.getName(), Level.ERROR, null,
                // Logged message is not from API.errorMessage.
                "||level=>ERROR||method=>TestApiLogFuncService.customMessage||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0");
    }

    @Test
    void testStackTrace() {
        testApiLogFuncService.stackTrace();

        assertLog(TestApiLogFuncService.class.getName(), Level.ERROR,
                // Has no stack trace since it's not a SysError.
                MyException.class,
                "||level=>ERROR||method=>TestApiLogFuncService.stackTrace||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0||error=>io.codeone.framework.api.logging.domain.exception.MyException: Invalid param");
    }
}
