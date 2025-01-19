package io.codeone.framework.ext.bizscenario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class BizScenarioParamRepoTest {

    @Autowired
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Test
    public void getParamIndex() {
        Assertions.assertEquals("No BizScenario source registered for method \"java.util.ArrayList.add(Object)\".",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> bizScenarioParamRepo.getParamIndex(ArrayList.class.getMethod("add", Object.class))).getMessage());
    }
}