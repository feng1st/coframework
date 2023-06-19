package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.domain.param.MyParam;
import io.codeone.framework.logging.domain.service.TestLogPresetService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LogPresetTests extends BaseLogTests {

    @Resource
    private TestLogPresetService testLogPresetService;

    @Test
    void testNoneSuccess() {
        testLogPresetService.none(new MyParam().setId(1L));

        assertLog(TestLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are not logged.
                "||level=>INFO||method=>TestLogPresetService.none||success=>true||elapsed=>0");
    }

    @Test
    void testAllSuccess() {
        testLogPresetService.all(new MyParam().setId(1L));

        assertLog(TestLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are logged.
                "||level=>INFO||method=>TestLogPresetService.all||success=>true||elapsed=>0||arg.param=>BaseRequest{bizScenario=null}||result=>1");
    }

    @Test
    void testNoneError() {
        testLogPresetService.none(new MyParam());

        assertLog(TestLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is not logged.
                null,
                // arg.param and error are not logged.
                "||level=>ERROR||method=>TestLogPresetService.none||success=>false||code=>INVALID_PARAM||message=>id is null||elapsed=>0");
    }

    @Test
    void testAllError() {
        testLogPresetService.all(new MyParam());

        assertLog(TestLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is logged.
                IllegalArgumentException.class,
                // arg.param and error are logged.
                "||level=>ERROR||method=>TestLogPresetService.all||success=>false||code=>INVALID_PARAM||message=>id is null||elapsed=>0||arg.param=>BaseRequest{bizScenario=null}||error=>java.lang.IllegalArgumentException: id is null");
    }
}
