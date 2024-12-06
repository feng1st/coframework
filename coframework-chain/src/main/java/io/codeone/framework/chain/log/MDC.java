package io.codeone.framework.chain.log;

import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class MDC {

    private final ThreadLocal<Deque<Map<Object, Object>>> THREAD_LOCAL = ThreadLocal.withInitial(LinkedList::new);

    public boolean wrap(Function<Map<Object, Object>, Boolean> function) {
        Deque<Map<Object, Object>> stack = THREAD_LOCAL.get();
        Map<Object, Object> mdc = new LinkedHashMap<>();
        stack.push(mdc);
        try {
            return function.apply(mdc);
        } finally {
            stack.pop();
        }
    }

    public void put(Object key, Object value) {
        Map<Object, Object> map = THREAD_LOCAL.get().peek();
        if (map != null) {
            map.put(key, value);
        }
    }
}
