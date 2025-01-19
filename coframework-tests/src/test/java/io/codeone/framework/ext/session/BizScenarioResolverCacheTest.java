package io.codeone.framework.ext.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BizScenarioResolverCacheTest {

    @Autowired
    private BizScenarioResolverCache bizScenarioResolverCache;

    @Test
    public void getResolver() {
        Assertions.assertEquals("Failed to load BizScenarioResolver \"BizScenarioResolverCacheTestInvalidResolver\". Ensure the resolver is properly registered as a Spring bean.",
                Assertions.assertThrows(IllegalStateException.class,
                        () -> bizScenarioResolverCache.getResolver(BizScenarioResolverCacheTestInvalidResolver.class)).getMessage());
    }
}