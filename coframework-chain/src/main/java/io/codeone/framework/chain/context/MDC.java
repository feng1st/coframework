package io.codeone.framework.chain.context;

import java.util.*;

public class MDC {

    private final ThreadLocal<Deque<Map<Object, Object>>> threadLocal = new ThreadLocal<>();

    public void put(Object key, Object value) {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        Objects.requireNonNull(stack);
        Map<Object, Object> map = stack.peek();
        Objects.requireNonNull(map);
        map.put(key, value);
    }

    public void stackPush() {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        if (stack == null) {
            stack = new LinkedList<>();
            threadLocal.set(stack);
        }
        stack.push(new LinkedHashMap<>());
    }

    public Map<Object, Object> stackPop() {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        Objects.requireNonNull(stack);
        Map<Object, Object> map = stack.pop();
        if (stack.isEmpty()) {
            threadLocal.remove();
        }
        return map;
    }
}
