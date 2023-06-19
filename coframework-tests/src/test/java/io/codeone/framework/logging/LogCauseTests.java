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
        testLogCauseService.apiError();

        assertLog(TestLogCauseService.class.getName(), Level.WARN, null,
                // Cause found is MyException(CommonErrors.INVALID_PARAM, ...).
                "||level=>WARN||method=>TestLogCauseService.apiError||success=>false||code=>INVALID_PARAM||message=>Invalid param||elapsed=>0");
    }

    @Test
    void testInvalidParam() {
        testLogCauseService.invalidParam();

        assertLog(TestLogCauseService.class.getName(), Level.ERROR, null,
                // Cause found is IllegalArgumentException("Negative", ...).
                "||level=>ERROR||method=>TestLogCauseService.invalidParam||success=>false||code=>INVALID_PARAM||message=>Negative||elapsed=>0");
    }

    @Test
    void testOtherException() {
        testLogCauseService.otherException();

        assertLog(TestLogCauseService.class.getName(), Level.ERROR, null,
                // Cause found is Exception("Deeply sorry").
                "||level=>ERROR||method=>TestLogCauseService.otherException||success=>false||code=>Exception||message=>Deeply sorry||elapsed=>0");
    }
}
