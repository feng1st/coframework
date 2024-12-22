package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.context.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SequentialTest {

    @Test
    public void sequential() {
        Assertions.assertEquals(Arrays.asList(1, 2), Sequential.of(context -> {
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    return true;
                }).run(Context.of("key", new ArrayList<>()), "key"));
    }

    @Test
    public void sequentialBreak() {
        Assertions.assertEquals(Arrays.asList(1, 2), Sequential.of(context -> {
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    return false;
                },
                context -> {
                    context.<List<Integer>>get("key").add(3);
                    return true;
                }).run(Context.of("key", new ArrayList<>()), "key"));
    }
}