package io.codeone.framework.ext.bizscenario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.ArrayList;

@SpringBootTest
class BizScenarioParamRepoTest {

    @Autowired
    private BizScenarioParamRepo bizScenarioParamRepo;

    @Test
    public void getParamIndex() {
        Method method = ArrayList.class.getMethods()[0];
        Assertions.assertThrows(IllegalStateException.class,
                () -> bizScenarioParamRepo.getParamIndex(method));
    }
}