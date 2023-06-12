package io.codeone.framework.ext.resolve;


import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.constants.TestExtConstants;
import io.codeone.framework.ext.resolve.domain.service.TestResolveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ExtResolveTests {

    @Resource
    private TestResolveService testResolveService;

    @Test
    void testByClass() {
        BizScenario param1 = BizScenario.ofBizId(TestExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(TestExtConstants.BIZ2);

        Assertions.assertEquals("1", testResolveService.genCodeClassAutoFirst(param1, param2));
        Assertions.assertEquals("2", testResolveService.genCodeClassAutoSpecified(param1, param2));
    }

    @Test
    void testByMethod() {
        BizScenario param1 = BizScenario.ofBizId(TestExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(TestExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(TestExtConstants.BIZ3);

        Assertions.assertEquals("1", testResolveService.genCodeFirst(param1, param2, param3));
        Assertions.assertEquals("3", testResolveService.genCodeLast(param1, param2, param3));
        Assertions.assertEquals("2", testResolveService.genCodeSpecified(param1, param2, param3));
    }

    @Test
    void testAuto() {
        BizScenario param1 = BizScenario.ofBizId(TestExtConstants.BIZ1);
        BizScenario param2 = BizScenario.ofBizId(TestExtConstants.BIZ2);
        BizScenario param3 = BizScenario.ofBizId(TestExtConstants.BIZ3);

        Assertions.assertEquals("1", testResolveService.genCodeAutoFirst(param1, param2, param3));
        Assertions.assertEquals("2", testResolveService.genCodeAutoSpecified(param1, param2, param3));
        Assertions.assertEquals("2", testResolveService.genCodeAutoCustom("John"));
        Assertions.assertEquals("3", testResolveService.genCodeAutoCustom("Bob"));
        Assertions.assertEquals("1", testResolveService.genCodeAutoCustom("Jim"));
    }

    @Test
    void testCustom() {
        Assertions.assertEquals("2", testResolveService.genCodeCustom("John"));
        Assertions.assertEquals("3", testResolveService.genCodeCustom("Bob"));
        Assertions.assertEquals("1", testResolveService.genCodeCustom("Jim"));
    }
}
