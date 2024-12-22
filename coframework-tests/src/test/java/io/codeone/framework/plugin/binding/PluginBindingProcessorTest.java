package io.codeone.framework.plugin.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootTest
class PluginBindingProcessorTest {

    @Autowired
    private PluginBindingProcessorTestService pluginBindingProcessorTestService;

    @Test
    public void method() {
        Assertions.assertEquals(Collections.singletonList("processed"),
                pluginBindingProcessorTestService.method(new ArrayList<>()));
    }
}