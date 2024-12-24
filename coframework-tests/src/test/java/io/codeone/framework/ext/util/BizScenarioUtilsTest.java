package io.codeone.framework.ext.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizScenarioUtilsTest {

    @Test
    public void join() {
        Assertions.assertNull(BizScenarioUtils.join(null));
        Assertions.assertNull(BizScenarioUtils.join(new String[0]));
    }
}
