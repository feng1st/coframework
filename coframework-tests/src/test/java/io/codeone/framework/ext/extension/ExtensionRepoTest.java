package io.codeone.framework.ext.extension;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class ExtensionRepoTest {

    @Autowired
    private ExtensionRepo extensionRepo;

    @Test
    public void getExtension() {
        Assertions.assertEquals("No Extension found for 'ArrayList[*|*]'",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> extensionRepo.getExtension(ArrayList.class, BizScenario.of())).getMessage());
    }

    @Test
    public void register() throws Exception {
        Method method = ExtensionRepoImpl.class.getDeclaredMethod("register", Object.class);
        method.setAccessible(true);
        Assertions.assertEquals("'Object' does not implement any Extensible interface (annotated with @Ability or @ExtensionPoint)",
                Assertions.assertThrows(InvocationTargetException.class,
                        () -> method.invoke(extensionRepo, new Object())).getCause().getMessage());
    }

    @Test
    public void registerExtension() throws Exception {
        Method method = ExtensionRepoImpl.class.getDeclaredMethod("registerExtension", Class.class, BizScenario.class, Object.class);
        method.setAccessible(true);
        method.invoke(extensionRepo, List.class, BizScenario.ofScenario("s"), new ArrayList<>());
        Assertions.assertEquals("Duplicate Extension found for 'List': existing 'ArrayList' vs new 'LinkedList'",
                Assertions.assertThrows(InvocationTargetException.class,
                        () -> method.invoke(extensionRepo, List.class, BizScenario.ofScenario("s"), new LinkedList<>())).getCause().getMessage());
    }
}