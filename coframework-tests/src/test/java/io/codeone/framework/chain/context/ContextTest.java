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
        Typed key = ContextTestTyped.INT;
        context.put(key, 1);
        Assertions.assertThrows(ClassCastException.class, () -> context.<String>ifPresent(key, o -> {
        }));
        Assertions.assertThrows(ClassCastException.class, () -> context.getOrDefault(key, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.put(key, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.putIfAbsent(key, "value"));
    }

    @Test
    public void putAndGetClassInvalid() {
        Context context = Context.of();
        Class<Integer> key = Integer.class;
        context.put(key, 1);
        Assertions.assertThrows(ClassCastException.class, () -> context.<String>ifPresent(key, o -> {
        }));
        Assertions.assertThrows(ClassCastException.class, () -> context.getOrDefault(key, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.put(key, "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.putIfAbsent(key, "value"));
    }

    @Test
    public void compute() {
        Context context = Context.of();
        Object key = new Object();
        Assertions.assertNull(context.computeIfPresent(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("value", context.computeIfAbsent(key, k -> "value"));
        Assertions.assertEquals("value", context.get(key));
        Assertions.assertEquals("new value", context.computeIfPresent(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
        Assertions.assertEquals("new value", context.get(key));
        Assertions.assertNull(context.compute(key, (k, v) -> {
            Assertions.assertEquals("new value", v);
            return null;
        }));
        Assertions.assertEquals("value", context.compute(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertEquals("new value", context.compute(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
    }

    @Test
    public void computeTyped() {
        Context context = Context.of();
        Typed key = ContextTestTyped.STR;
        Assertions.assertNull(context.computeIfPresent(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("value", context.computeIfAbsent(key, k -> "value"));
        Assertions.assertEquals("value", context.get(key));
        Assertions.assertEquals("new value", context.computeIfPresent(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
        Assertions.assertEquals("new value", context.get(key));
        Assertions.assertNull(context.compute(key, (k, v) -> {
            Assertions.assertEquals("new value", v);
            return null;
        }));
        Assertions.assertEquals("value", context.compute(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertEquals("new value", context.compute(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
    }

    @Test
    public void computeClass() {
        Context context = Context.of();
        Class<String> key = String.class;
        Assertions.assertNull(context.computeIfPresent(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertNull(context.get(key));
        Assertions.assertEquals("value", context.computeIfAbsent(key, k -> "value"));
        Assertions.assertEquals("value", context.get(key));
        Assertions.assertEquals("new value", context.computeIfPresent(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
        Assertions.assertEquals("new value", context.get(key));
        Assertions.assertNull(context.compute(key, (k, v) -> {
            Assertions.assertEquals("new value", v);
            return null;
        }));
        Assertions.assertEquals("value", context.compute(key, (k, v) -> {
            Assertions.assertNull(v);
            return "value";
        }));
        Assertions.assertEquals("new value", context.compute(key, (k, v) -> {
            Assertions.assertEquals("value", v);
            return "new value";
        }));
    }

    @Test
    public void computeTypedInvalid() {
        Context context = Context.of();
        Typed key = ContextTestTyped.INT;
        Assertions.assertThrows(ClassCastException.class, () -> context.computeIfAbsent(key, k -> "value"));
        context.put(key, 1);
        Assertions.assertThrows(ClassCastException.class, () -> context.computeIfPresent(key, (k, v) -> "value"));
        Assertions.assertThrows(ClassCastException.class, () -> context.compute(key, (k, v) -> "value"));
    }
}