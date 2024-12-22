package io.codeone.framework.plugin.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class EnablePluginTest {

    @Autowired
    private EnablePluginTestService enablePluginTestService;

    @Test
    public void staticMethod() {
        Assertions.assertEquals(Collections.emptyList(),
                enablePluginTestService.staticMethod(new ArrayList<>()));
    }

    @Test
    public void empty() {
        Assertions.assertEquals(Collections.emptyList(),
                enablePluginTestService.empty(new ArrayList<>()));
    }

    @Test
    public void foo() {
        Assertions.assertEquals(Collections.singletonList("foo"),
                enablePluginTestService.foo(new ArrayList<>()));
    }

    @Test
    public void fooBar() {
        Assertions.assertEquals(Arrays.asList("foo", "bar"),
                enablePluginTestService.fooBar(new ArrayList<>()));
    }
}
