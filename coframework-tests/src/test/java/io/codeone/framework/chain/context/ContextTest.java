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

    @Test
    public void putAndGetTypedInvalid() {
        Context context = Context.of();
        context.put(ContextTestTyped.INT, 1);
        Assertions.assertThrows(ClassCastException.class, () -> context.<String>ifPresent(ContextTestTyped.INT, o -> {
        }));
        Assertions.assertThrows(ClassCastException.class, () -> context.getOrDefault(ContextTestTyped.INT, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.put(ContextTestTyped.INT, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.putIfAbsent(ContextTestTyped.INT, "value"));
    }

    @Test
    public void putAndGetClassInvalid() {
        Context context = Context.of();
        context.put(Integer.class, 1);
        Assertions.assertThrows(ClassCastException.class, () -> context.<String>ifPresent(Integer.class, o -> {
        }));
        Assertions.assertThrows(ClassCastException.class, () -> context.getOrDefault(Integer.class, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.put(Integer.class, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.putIfAbsent(Integer.class, "value"));
    }
}