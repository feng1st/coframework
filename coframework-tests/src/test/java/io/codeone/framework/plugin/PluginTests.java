package io.codeone.framework.plugin;

import io.codeone.framework.plugin.domain.service.TestPluginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PluginTests {

    private static final long RESULT_IF_ERROR = 0L;

    @Autowired
    private TestPluginService testPluginService;

    @Test
    void testAltering() {
        long a = 10L;
        long b = 9L;
        Assertions.assertEquals(((a + 1L) + (b + 1L)) - 5L, testPluginService.sum(a, b));
    }

    @Test
    void testArgChecking() {
        long a = 10L;
        long b = 11L;
        // Arg1 is too large.
        Assertions.assertEquals(RESULT_IF_ERROR, testPluginService.sum(a, b));
    }

    @Test
    void testResultChecking() {
        long a = 10L;
        long b = 10L;
        // Result is too large.
        Assertions.assertEquals(RESULT_IF_ERROR, testPluginService.sum(a, b));
    }

    @Test
    void testNoChecking() {
        {
            long a = 10L;
            long b = 11L;
            Assertions.assertEquals(((a + 1L) + (b + 1L)) - 5L, testPluginService.sumNoChecking(a, b));
        }
        {
            long a = 10L;
            long b = 10L;
            Assertions.assertEquals(((a + 1L) + (b + 1L)) - 5L, testPluginService.sumNoChecking(a, b));
        }
    }

    @Test
    void testNoAltering() {
        {
            long a = 10L;
            long b = 10L;
            Assertions.assertEquals(a + b, testPluginService.sumNoAltering(a, b));
        }
        {
            long a = 10L;
            long b = 11L;
            // Arg1 is too large.
            Assertions.assertEquals(RESULT_IF_ERROR, testPluginService.sumNoAltering(a, b));
        }
    }

    @Test
    void testWrapping() {
        try {
            long a = 10L;
            long b = 11L;
            testPluginService.sumNoWrapping(a, b);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Arg1 is too large", e.getMessage());
        }
        try {
            long a = 10L;
            long b = 10L;
            testPluginService.sumNoWrapping(a, b);
            Assertions.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Result is too large", e.getMessage());
        }
    }

    @Test
    void testDup() {
        long a = 10L;
        long b = 10L;
        // Only add once.
        Assertions.assertEquals((a + 1L) + (b + 1L), testPluginService.dupPlugins(a, b));
    }

    @Test
    void testOverriddenMethodOfObject() {
        Assertions.assertNotEquals("", testPluginService.toString());
    }
}
