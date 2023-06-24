package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.logging.domain.param.MyParam;
import io.codeone.framework.logging.domain.service.TestLogSpelService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class LogSpelTests extends BaseLogTests {

    @Resource
    private TestLogSpelService testLogSpelService;

    @Test
    void testKeyPairs() {
        testLogSpelService.keyPairs(new MyParam().setId(1L).setAddress(new MyParam.Address().setCity("test")));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // arg.bizId, arg.id and arg.ret have values.
                "||level=>INFO||method=>TestLogSpelService.keyPairs||success=>true||elapsed=>0||arg.city=>test||arg.id=>1||arg.ret=>1");
    }

    @Test
    void testNullSafeNavigation() {
        testLogSpelService.keyPairs(new MyParam().setId(1L));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // '#arg0?.bizScenario?.bizId' doesn't throw when bizScenario is null.
                "||level=>INFO||method=>TestLogSpelService.keyPairs||success=>true||elapsed=>0||arg.id=>1||arg.ret=>1");
    }

    @Test
    void testExpSuccessTrue() {
        testLogSpelService.code(new MyParam().setId(1L));

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // success=>true by evaluation.
                "||level=>INFO||method=>TestLogSpelService.code||success=>true||code=>1||message=>1||elapsed=>0");
    }

    @Test
    void testExpSuccessFalse() {
        testLogSpelService.code(new MyParam().setId(2L));

        assertLog(TestLogSpelService.class.getName(), Level.WARN, null,
                // success=>false by evaluation.
                "||level=>WARN||method=>TestLogSpelService.code||success=>false||code=>2||message=>2||elapsed=>0");
    }
}
