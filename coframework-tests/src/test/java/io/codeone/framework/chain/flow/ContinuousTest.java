package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.context.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContinuousTest {

    @Autowired
    private ContinuousTestNode continuousTestNode;

    @Test
    public void continuous() {
        Assertions.assertEquals("value", continuousTestNode.run(Context.of(), "key"));
    }
}