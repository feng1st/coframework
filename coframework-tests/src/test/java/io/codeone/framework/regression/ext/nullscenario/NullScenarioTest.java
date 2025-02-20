package io.codeone.framework.regression.ext.nullscenario;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Regression tests for edge case handling of {@link BizScenario} with null properties,
 * simulating scenarios that might occur from some serialization/deserialization
 * frameworks.
 *
 * <p>Validates compatibility behaviors including:
 * <ul>
 *   <li>String representation via {@code toString()}
 *   <li>Iterator traversal consistency
 * </ul>
 *
 * <p>Test scenario: Artificially sets the 'scenario' property to null to verify:
 * <ol>
 *   <li>Maintained string format convention despite missing components
 *   <li>Proper iteration sequence through decomposed scenarios
 * </ol>
 */
public class NullScenarioTest {

    @Test
    void test() throws Exception {
        BizScenario bizScenario = BizScenario.ofBizId("foo.bar");
        Field scenarioField = BizScenario.class.getDeclaredField("scenario");
        scenarioField.setAccessible(true);
        scenarioField.set(bizScenario, null);

        Assertions.assertEquals("*", bizScenario.getScenario());

        Assertions.assertEquals("foo.bar|*", bizScenario.toString());

        Iterator<BizScenario> iter = bizScenario.iterator();
        Assertions.assertEquals("foo.bar|*", iter.next().toString());
        Assertions.assertEquals("foo|*", iter.next().toString());
        Assertions.assertEquals("*|*", iter.next().toString());
        Assertions.assertFalse(iter.hasNext());
    }
}
