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
        Assertions.assertThrows(IllegalStateException.class,
                () -> extensionSessionRepo.getParamIndex(ArrayList.class.getMethods()[0]));
    }
}