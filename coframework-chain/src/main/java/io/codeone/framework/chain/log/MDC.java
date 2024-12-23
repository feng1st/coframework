package io.codeone.framework.chain.log;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;

/**
 * Provides thread-local scoped logging for execution contexts.
 *
 * <p>This utility manages thread-local storage for logging context, offering scoped
 * operations with lifecycle management.
 */
@UtilityClass
public class MDC {

    private final ThreadLocal<Deque<Map<Object, Object>>> THREAD_LOCAL = ThreadLocal.withInitial(LinkedList::new);

    /**
     * Executes a function within a scoped logging context.
     *
     * @param function the function to execute
     * @return the result of the function execution
     */
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

    /**
     * Adds a key-value pair to the current logging context.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(Object key, Object value) {
        Map<Object, Object> map = THREAD_LOCAL.get().peek();
        Objects.requireNonNull(map);
        map.put(key, value);
    }
}
