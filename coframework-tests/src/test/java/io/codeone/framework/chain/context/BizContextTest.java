package io.codeone.framework.chain.context;

import io.codeone.framework.ext.BizScenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class BizContextTest {

    @Test
    public void build() {
        BizContext context;
        context = BizContext.of(BizScenario.ofBizId("foo"));
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        context = BizContext.of(BizScenario.ofBizId("foo"), "1", 1);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        context = BizContext.of(BizScenario.ofBizId("foo"), "1", 1, "2", 2);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = BizContext.of(BizScenario.ofBizId("foo"), "1", 1, "2", 2, "3", 3);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        context = BizContext.of(BizScenario.ofBizId("foo"), "1", 1, "2", 2, "3", 3, "4", 4);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        Assertions.assertEquals(4, context.<Integer>get("4"));
        context = BizContext.of(BizScenario.ofBizId("foo"), "1", 1, "2", 2, "3", 3, "4", 4, "5", 5);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        Assertions.assertEquals(4, context.<Integer>get("4"));
        Assertions.assertEquals(5, context.<Integer>get("5"));
        Map<Object, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        context = BizContext.of(BizScenario.ofBizId("foo"), map);
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = BizContext.of(BizScenario.ofBizId("foo"), Context.of("1", 1, "2", 2));
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = BizContext.of(BizContext.of(BizScenario.ofBizId("foo"), "1", 1, "2", 2));
        Assertions.assertEquals(BizScenario.ofBizId("foo"), context.getBizScenario());
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
    }
}
