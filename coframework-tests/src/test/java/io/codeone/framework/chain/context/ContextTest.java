package io.codeone.framework.chain.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

class ContextTest {

    @Test
    public void putAndGet() {
        Context context = Context.of();
        Object key = new Object();
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("default value", context.getOrDefault(key, "default value"));
        Assertions.assertNull(context.putIfAbsent(key, null));
        Assertions.assertNull(context.putIfAbsent(key, "value"));
        Assertions.assertEquals("value", context.putIfAbsent(key, null));
        Assertions.assertEquals("value", context.putIfAbsent(key, "new value"));
        Assertions.assertEquals("value", context.put(key, "new value"));
        Assertions.assertEquals("new value", context.put(key, null));
        Assertions.assertNull(context.get(key));
        Assertions.assertNull(context.put(key, "value"));
        Assertions.assertEquals("value", context.get(key));
        AtomicReference<String> valueHolder = new AtomicReference<>();
        context.ifPresent(key, valueHolder::set);
        Assertions.assertEquals("value", valueHolder.get());
        Assertions.assertEquals("value", context.remove(key));
        context.ifPresent(key, o -> {
            throw new IllegalStateException();
        });
        Assertions.assertNull(context.get(key));
    }

    @Test
    public void putAndGetTyped() {
        Context context = Context.of();
        Typed key = ContextTestTyped.STR;
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("default value", context.getOrDefault(key, "default value"));
        Assertions.assertNull(context.putIfAbsent(key, null));
        Assertions.assertNull(context.putIfAbsent(key, "value"));
        Assertions.assertEquals("value", context.putIfAbsent(key, null));
        Assertions.assertEquals("value", context.putIfAbsent(key, "new value"));
        Assertions.assertEquals("value", context.put(key, "new value"));
        Assertions.assertEquals("new value", context.put(key, null));
        Assertions.assertNull(context.get(key));
        Assertions.assertNull(context.put(key, "value"));
        Assertions.assertEquals("value", context.get(key));
        AtomicReference<String> valueHolder = new AtomicReference<>();
        context.ifPresent(key, valueHolder::set);
        Assertions.assertEquals("value", valueHolder.get());
        Assertions.assertEquals("value", context.remove(key));
        context.ifPresent(key, o -> {
            throw new IllegalStateException();
        });
        Assertions.assertNull(context.get(key));
    }

    @Test
    public void putAndGetClass() {
        Context context = Context.of();
        Class<String> key = String.class;
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("default value", context.getOrDefault(key, "default value"));
        Assertions.assertNull(context.putIfAbsent(key, null));
        Assertions.assertNull(context.putIfAbsent(key, "value"));
        Assertions.assertEquals("value", context.putIfAbsent(key, null));
        Assertions.assertEquals("value", context.putIfAbsent(key, "new value"));
        Assertions.assertEquals("value", context.put(key, "new value"));
        Assertions.assertEquals("new value", context.put(key, null));
        Assertions.assertNull(context.get(key));
        Assertions.assertNull(context.put(key, "value"));
        Assertions.assertEquals("value", context.get(key));
        AtomicReference<String> valueHolder = new AtomicReference<>();
        context.ifPresent(key, valueHolder::set);
        Assertions.assertEquals("value", valueHolder.get());
        Assertions.assertEquals("value", context.remove(key));
        context.ifPresent(key, o -> {
            throw new IllegalStateException();
        });
        Assertions.assertNull(context.get(key));
    }
}