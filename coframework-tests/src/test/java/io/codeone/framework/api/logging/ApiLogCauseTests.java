package io.codeone.framework.api.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.logging.domain.service.TestApiLogCauseService;
import io.codeone.framework.logging.BaseLogTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiLogCauseTests extends BaseLogTests {

    @Resource
    private TestApiLogCauseService testApiLogCauseService;

    @Test
    void testApiError() {
        testApiLogCauseService.apiError();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, null,
                // Cause found is MyException(CommonErrors.INVALID_PARAM, ...).
                "||level=>ERROR||method=>TestApiLogCauseService.apiError||success=>false||code=>INVALID_PARAM||message=>Invalid parameters||elapsed=>0");
    }

    @Test
    void testInvalidParam() {
        testApiLogCauseService.invalidParam();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, null,
                // Cause found is IllegalArgumentException("Negative", ...).
                "||level=>ERROR||method=>TestApiLogCauseService.invalidParam||success=>false||code=>INVALID_PARAM||message=>Negative||elapsed=>0");
    }

    @Test
    void testOtherException() {
        testApiLogCauseService.otherException();

        assertLog(TestApiLogCauseService.class.getName(), Level.ERROR, null,
                // Cause found is Exception("Deeply sorry").
                "||level=>ERROR||method=>TestApiLogCauseService.otherException||success=>false||code=>Exception||message=>Deeply sorry||elapsed=>0");
    }
}
