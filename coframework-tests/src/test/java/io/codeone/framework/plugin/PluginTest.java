package io.codeone.framework.plugin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class PluginTest {

    @Autowired
    private PluginTestService pluginTestService;

    @Test
    public void returning() {
        List<Object> param = new ArrayList<>();
        pluginTestService.returning(param);
        Assertions.assertEquals(Arrays.asList("result-before",
                        "after-1-before",
                        "after-2-before",
                        "arg-before",
                        "before-1-before",
                        "before-2-before",
                        "before-2-after-returning",
                        "before-1-after-returning",
                        "arg-after-returning",
                        "after-2-after-returning",
                        "after-1-after-returning",
                        "result-after-returning"),
                param);
    }

    @Test
    public void throwing() {
        List<Object> param = new ArrayList<>();
        Assertions.assertThrows(IllegalStateException.class,
                () -> pluginTestService.throwing(param));
        Assertions.assertEquals(Arrays.asList("result-before",
                        "after-1-before",
                        "after-2-before",
                        "arg-before",
                        "before-1-before",
                        "before-2-before",
                        "before-2-after-throwing",
                        "before-1-after-throwing",
                        "arg-after-throwing",
                        "after-2-after-throwing",
                        "after-1-after-throwing",
                        "result-after-throwing"),
                param);
    }
}