package io.codeone.framework.ext.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class ExtensionSessionRepoTest {

    @Autowired
    private ExtensionSessionRepo extensionSessionRepo;

    @Test
    public void getParamIndex() {
        Assertions.assertEquals("No BizScenario source found for method 'public boolean java.util.ArrayList.add(java.lang.Object)'",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> extensionSessionRepo.getParamIndex(ArrayList.class.getMethod("add", Object.class))).getMessage());
    }
}