package io.codeone.framework.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClassUtilsTest {

    @Autowired
    private ClassUtilsTestService classUtilsTestService;

    @Test
    public void forName() {
        Assertions.assertThrows(ClassNotFoundException.class,
                () -> ClassUtils.forName("not-a-class", null));
    }

    @Test
    public void getTargetClass() {
        Assertions.assertNotEquals(classUtilsTestService.getClass(),
                ClassUtils.getTargetClass(classUtilsTestService));
    }
}