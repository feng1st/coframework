package io.codeone.framework.legacy;

import ch.qos.logback.classic.Level;
import io.codeone.framework.legacy.domain.param.MyParam;
import io.codeone.framework.legacy.domain.service.TestLogSpelService;
import io.codeone.framework.logging.BaseLoggingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogSpelTests extends BaseLoggingTest {

    @Autowired
    private TestLogSpelService testLogSpelService;

    @Test
    void testKeyPairs() {
        testLogSpelService.keyPairs(new MyParam().setId(1L).setAddress(new MyParam.Address().setCity("test")));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // arg.bizId, arg.id and arg.ret have values.
                "{level=INFO, method=TestLogSpelService.keyPairs, success=true, elapsed=0, args={city=test, id=1, ret=1}, result=MyParam(id=1, address=MyParam.Address(city=test))}");
    }

    @Test
    void testNullSafeNavigation() {
        testLogSpelService.keyPairs(new MyParam().setId(1L));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // '#arg0?.bizScenario?.bizId' doesn't throw when bizScenario is null.
                "{level=INFO, method=TestLogSpelService.keyPairs, success=true, elapsed=0, args={city=null, id=1, ret=1}, result=MyParam(id=1, address=null)}");
    }

    @Test
    void testExpSuccessTrue() {
        testLogSpelService.code(new MyParam().setId(1L));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // success=>true by evaluation.
                "{level=INFO, method=TestLogSpelService.code, success=true, code=1, message=1, elapsed=0, args={param=MyParam(id=1, address=null)}, result=MyParam(id=1, address=null)}");
    }

    @Test
    void testExpSuccessFalse() {
        testLogSpelService.code(new MyParam().setId(2L));

        assertLog(TestLogSpelService.class.getName(), Level.WARN, null,
                // success=>false by evaluation.
                "{level=WARN, method=TestLogSpelService.code, success=false, code=2, message=2, elapsed=0, args={param=MyParam(id=2, address=null)}, result=MyParam(id=2, address=null)}");
    }
}
