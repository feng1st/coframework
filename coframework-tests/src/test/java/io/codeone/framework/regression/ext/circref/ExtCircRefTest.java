package io.codeone.framework.regression.ext.circref;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Regression test verifying resolution of circular dependency in extension infrastructure.
 *
 * <h3>Original Problem</h3>
 * A circular dependency chain was detected during dependency injection:
 * <pre>{@code
 * ExtensionRepoImpl --> ExtensibleInvocationHandler (proxy)
 *       ^                      |
 *       |                      |
 *       +----------------------+
 * }</pre>
 * This occurred because:
 * <ul>
 *   <li>{@code ExtensionRepoImpl} depends on extension proxies</li>
 *   <li>{@code ExtensibleInvocationHandler} proxies require {@code ExtensionRepo}</li>
 * </ul>
 *
 * <h3>Implemented Solution</h3>
 * The circular dependency was resolved by:
 * <ol>
 *   <li>Annotating {@code ExtensibleInvocationHandler.extensionRepo} field with
 *   {@code @Lazy}</li>
 *   <li>Deferring actual repository resolution until first use through lazy initialization</li>
 * </ol>
 *
 * <h3>Verification Scope</h3>
 * This test validates that:
 * <ul>
 *   <li>Application context initializes without circular dependency errors</li>
 *   <li>Extension resolution works post-initialization through the lazy proxy</li>
 *   <li>No eager initialization occurs during dependency injection phase</li>
 * </ul>
 */
@SpringBootTest
public class ExtCircRefTest {

    @Autowired
    private ExtCircRefTestEp extCircRefTestEp;

    @Test
    void test() throws Throwable {
        BizScenarioContext.invoke(BizScenario.of(),
                () -> extCircRefTestEp.test());
    }
}
