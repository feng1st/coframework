package io.codeone.framework.api.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.logging.domain.param.MyBizParam;
import io.codeone.framework.api.logging.domain.service.TestApiLogPresetService;
import io.codeone.framework.logging.BaseLogTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiLogPresetTests extends BaseLogTests {

    @Autowired
    private TestApiLogPresetService testApiLogPresetService;

    @Test
    void testNoneSuccess() {
        testApiLogPresetService.none(new MyBizParam().setId(1L));

        assertLog(TestApiLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are not logged.
                "{level=INFO, method=TestApiLogPresetService.none, success=true, elapsed=0}");
    }

    @Test
    void testAllSuccess() {
        testApiLogPresetService.all(new MyBizParam().setId(1L));

        assertLog(TestApiLogPresetService.class.getName(), Level.INFO, null,
                // arg.param and result are logged.
                "{level=INFO, method=TestApiLogPresetService.all, success=true, elapsed=0, args={param=MyParam(super=BaseParam(bizScenario=null), id=1)}, result=1}");
    }

    @Test
    void testNoneError() {
        testApiLogPresetService.none(new MyBizParam());

        assertLog(TestApiLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is not logged.
                null,
                // arg.param and error are not logged.
                "{level=ERROR, method=TestApiLogPresetService.none, success=false, code=INVALID_ARGS, message=id is null, elapsed=0, exception=java.lang.IllegalArgumentException: id is null}");
    }

    @Test
    void testAllError() {
        testApiLogPresetService.all(new MyBizParam());

        assertLog(TestApiLogPresetService.class.getName(), Level.ERROR,
                // Stack trace is logged.
                IllegalArgumentException.class,
                // arg.param and error are logged.
                "{level=ERROR, method=TestApiLogPresetService.all, success=false, code=INVALID_ARGS, message=id is null, elapsed=0, args={param=MyParam(super=BaseParam(bizScenario=null), id=null)}, exception=java.lang.IllegalArgumentException: id is null}");
    }
}
