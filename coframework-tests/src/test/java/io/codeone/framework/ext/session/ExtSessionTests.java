package io.codeone.framework.ext.session;


import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.session.domain.service.TestExtSessionService;
import io.codeone.framework.ext.shared.constants.ExtConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExtSessionTests {

    @Autowired
    private TestExtSessionService testExtSessionService;

    @Test
    void testByClass() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);

        Assertions.assertEquals("1", testExtSessionService.genCodeClassAutoFirst(param1, param2));
        Assertions.assertEquals("2", testExtSessionService.genCodeClassAutoSpecified(param1, param2));
    }

    @Test
    void testByMethod() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testExtSessionService.genCodeFirst(param1, param2, param3));
        Assertions.assertEquals("3", testExtSessionService.genCodeLast(param1, param2, param3));
        Assertions.assertEquals("2", testExtSessionService.genCodeSpecified(param1, param2, param3));
    }

    @Test
    void testAuto() {
        BizScenario param1 = BizScenario.ofBizId(ExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(ExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(ExtConstants.BIZ3);

        Assertions.assertEquals("1", testExtSessionService.genCodeAutoFirst(param1, param2, param3));
        Assertions.assertEquals("2", testExtSessionService.genCodeAutoSpecified(param1, param2, param3));
        Assertions.assertEquals("2", testExtSessionService.genCodeAutoCustom("John"));
        Assertions.assertEquals("3", testExtSessionService.genCodeAutoCustom("Bob"));
        Assertions.assertEquals("1", testExtSessionService.genCodeAutoCustom("Jim"));
    }

    @Test
    void testCustom() {
        Assertions.assertEquals("2", testExtSessionService.genCodeCustom("John"));
        Assertions.assertEquals("3", testExtSessionService.genCodeCustom("Bob"));
        Assertions.assertEquals("1", testExtSessionService.genCodeCustom("Jim"));
    }
}
