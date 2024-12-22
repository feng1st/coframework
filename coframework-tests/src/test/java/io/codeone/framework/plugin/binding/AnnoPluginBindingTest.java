package io.codeone.framework.plugin.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
class AnnoPluginBindingTest {

    @Autowired
    private AnnoPluginBindingTestService annoPluginBindingTestService;

    @Test
    public void method() {
        Assertions.assertEquals(Arrays.asList("foo", "bar"),
                annoPluginBindingTestService.method(new ArrayList<>()));
    }
}