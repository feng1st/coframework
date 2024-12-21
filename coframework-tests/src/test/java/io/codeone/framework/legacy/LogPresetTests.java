package io.codeone.framework.legacy;

import ch.qos.logback.classic.Level;
import io.codeone.framework.legacy.domain.param.MyParam;
import io.codeone.framework.legacy.domain.service.TestLogPresetService;
import io.codeone.framework.logging.BaseLogTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogPresetTests extends BaseLogTests {

    @Autowired
    private TestLogPresetService testLogPresetService;

    @Test
    void testNoneSuccess() {
        testLogPresetService.none(new MyParam().setId(1L).setAddress(new MyParam.Address().setCity("test")));

        assertLog(TestLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are not logged.
                "{level=INFO, method=TestLogPresetService.none, success=true, elapsed=0}");
    }

    @Test
    void testAllSuccess() {
        testLogPresetService.all(new MyParam().setId(1L).setAddress(new MyParam.Address().setCity("test")));

        assertLog(TestLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are logged.
                "{level=INFO, method=TestLogPresetService.all, success=true, elapsed=0, args={param=MyParam(id=1, address=MyParam.Address(city=test))}, result=1}");
    }

    @Test
    void testNoneError() {
        try {
            testLogPresetService.none(new MyParam());
        } catch (Exception ignored) {
        }

        assertLog(TestLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is not logged.
                null,
                // arg.param and error are not logged.
                "{level=ERROR, method=TestLogPresetService.none, success=false, code=NullPointerException, elapsed=0, exception=java.lang.NullPointerException}");
    }

    @Test
    void testAllError() {
        try {
            testLogPresetService.all(new MyParam());
        } catch (Exception ignored) {
        }

        assertLog(TestLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is logged.
                NullPointerException.class,
                // arg.param and error are logged.
                "{level=ERROR, method=TestLogPresetService.all, success=false, code=NullPointerException, elapsed=0, args={param=MyParam(id=null, address=null)}, exception=java.lang.NullPointerException}");
    }
}
