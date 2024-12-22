package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.context.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConditionalTest {

    @Autowired
    private ConditionalTestNode conditionalTestNode;

    @Test
    public void componentTrue() {
        Assertions.assertTrue(conditionalTestNode.<Boolean>run(Context.of("test", true), "true"));
        Assertions.assertNull(conditionalTestNode.run(Context.of("test", true), "false"));
    }

    @Test
    public void componentFalse() {
        Assertions.assertTrue(conditionalTestNode.<Boolean>run(Context.of("test", false), "false"));
        Assertions.assertNull(conditionalTestNode.run(Context.of("test", false), "true"));
    }

    @Test
    public void plainIfTrue() {
        Assertions.assertTrue(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                }).<Boolean>run(Context.of("test", true), "true"));
    }

    @Test
    public void plainIfFalse() {
        Assertions.assertNull(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                }).run(Context.of("test", false), "true"));
    }

    @Test
    public void plainIfElseTrue() {
        Assertions.assertTrue(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                },
                context -> {
                    context.put("false", true);
                    return true;
                }).<Boolean>run(Context.of("test", true), "true"));
        Assertions.assertNull(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                },
                context -> {
                    context.put("false", true);
                    return true;
                }).run(Context.of("test", true), "false"));
    }

    @Test
    public void plainIfElseFalse() {
        Assertions.assertTrue(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                },
                context -> {
                    context.put("false", true);
                    return true;
                }).<Boolean>run(Context.of("test", false), "false"));
        Assertions.assertNull(Conditional.of(context -> context.<Boolean>get("test"),
                context -> {
                    context.put("true", true);
                    return true;
                },
                context -> {
                    context.put("false", true);
                    return true;
                }).run(Context.of("test", false), "true"));
    }
}