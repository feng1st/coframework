package io.codeone.framework.chain.context;

import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MDC {

    private final ThreadLocal<Deque<Map<Object, Object>>> threadLocal = new ThreadLocal<>();

    public void put(Object key, Object value) {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        assert stack != null;
        Map<Object, Object> mdc = stack.peek();
        assert mdc != null;
        mdc.put(key, value);
    }

    public void pushMdc() {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        if (stack == null) {
            stack = new LinkedList<>();
            threadLocal.set(stack);
        }
        stack.push(new LinkedHashMap<>());
    }

    public Map<Object, Object> popMdc() {
        Deque<Map<Object, Object>> stack = threadLocal.get();
        assert stack != null;
        Map<Object, Object> mdc = stack.pop();
        if (stack.isEmpty()) {
            threadLocal.remove();
        }
        return mdc;
    }
}
