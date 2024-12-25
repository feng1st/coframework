package io.codeone.framework.chain.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class ContextTest {

    @Test
    public void build() {
        Context context;
        context = Context.of("1", 1);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        context = Context.of("1", 1, "2", 2);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = Context.of("1", 1, "2", 2, "3", 3);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        context = Context.of("1", 1, "2", 2, "3", 3, "4", 4);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        Assertions.assertEquals(4, context.<Integer>get("4"));
        context = Context.of("1", 1, "2", 2, "3", 3, "4", 4, "5", 5);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        Assertions.assertEquals(3, context.<Integer>get("3"));
        Assertions.assertEquals(4, context.<Integer>get("4"));
        Assertions.assertEquals(5, context.<Integer>get("5"));
        Map<Object, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        context = Context.of(map);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = Context.of(Context.of("1", 1, "2", 2));
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
        context = Context.of().with("1", 1).with("2", 2);
        Assertions.assertEquals(1, context.<Integer>get("1"));
        Assertions.assertEquals(2, context.<Integer>get("2"));
    }

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

    @Test
    public void kvOp() {
        Context context = Context.of();
        Object key = new Object();
        Object subKey = new Object();
        Object subKey2 = new Object();
        Assertions.assertNull(context.kvGet(key, subKey));
        Assertions.assertNull(context.kvPut(key, subKey, "value"));
        Assertions.assertNull(context.kvPut(key, subKey2, "another value"));
        Assertions.assertEquals("value", context.kvGet(key, subKey));
        Assertions.assertEquals("value", context.kvPut(key, subKey, null));
        Assertions.assertEquals("another value", context.kvPut(key, subKey2, null));
        Assertions.assertNull(context.kvPut(key, subKey, null));
        Assertions.assertNull(context.kvGet(key, subKey));
        Assertions.assertEquals("value", context.kvComputeIfAbsent(key, subKey, k -> "value"));
        Assertions.assertEquals("value", context.kvGet(key, subKey));
        Assertions.assertEquals("value", context.kvComputeIfAbsent(key, subKey, k -> "new value"));
        Assertions.assertEquals("value", context.kvGet(key, subKey));
    }
}