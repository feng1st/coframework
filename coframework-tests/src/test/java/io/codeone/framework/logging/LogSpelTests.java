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
        MyParam param = new MyParam();
        param.setBizId("test");
        param.setId(1L);
        testLogSpelService.keyPairs(param);

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // arg.bizId, arg.id and arg.ret have values.
                "||level=>INFO||method=>TestLogSpelService.keyPairs||success=>true||elapsed=>0||arg.bizId=>test||arg.id=>1||arg.ret=>1");
    }

    @Test
    void testNullSafeNavigation() {
        MyParam param = new MyParam();
        param.setId(1L);
        testLogSpelService.keyPairs(param);

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // '#arg0?.bizScenario?.bizId' doesn't throw when bizScenario is null.
                "||level=>INFO||method=>TestLogSpelService.keyPairs||success=>true||elapsed=>0||arg.id=>1||arg.ret=>1");
    }

    @Test
    void testExpSuccessTrue() {
        MyParam param = new MyParam();
        param.setId(1L);
        testLogSpelService.code(param);

        assertLog(TestLogSpelService.class.getName(), Level.INFO, null,
                // success=>true by evaluation.
                "||level=>INFO||method=>TestLogSpelService.code||success=>true||code=>1||message=>1||elapsed=>0");
    }

    @Test
    void testExpSuccessFalse() {
        MyParam param = new MyParam();
        param.setId(2L);
        testLogSpelService.code(param);

        assertLog(TestLogSpelService.class.getName(), Level.ERROR, null,
                // success=>false by evaluation.
                "||level=>ERROR||method=>TestLogSpelService.code||success=>false||code=>2||message=>2||elapsed=>0");
    }
}
