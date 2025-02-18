package io.codeone.framework.chain.log;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Supplier;

/**
 * Manages thread-scoped parameters for structured logging within execution chains.
 *
 * <p>Maintains a stack-like context using {@link ThreadLocal} to track logging
 * parameters during nested operations. Integrates with {@link ChainLogger} for
 * context-aware logging.
 */
@UtilityClass
public class LoggingContext {

    private final ThreadLocal<Deque<Map<Object, Object>>> THREAD_LOCAL = ThreadLocal.withInitial(LinkedList::new);

    /**
     * Executes a supplier within a new logging context.
     *
     * <p>Creates a fresh context map, executes the operation, and automatically
     * cleans up the context upon completion. Ensures context isolation for nested
     * operations.
     *
     * @param invokable the operation to execute
     * @return the boolean result from the supplier
     */
    public boolean invoke(Supplier<Boolean> invokable) {
        Deque<Map<Object, Object>> stack = THREAD_LOCAL.get();
        Map<Object, Object> map = new LinkedHashMap<>();
        stack.push(map);
        try {
            return invokable.get();
        } finally {
            stack.pop();
        }
    }

    /**
     * Retrieves the current logging context map.
     *
     * @return the active context map from the top of the stack
     * @throws NullPointerException if no context is active (stack is empty)
     */
    public Map<Object, Object> getContextMap() {
        Map<Object, Object> map = THREAD_LOCAL.get().peek();
        Objects.requireNonNull(map);
        return map;
    }

    /**
     * Adds a parameter to the current logging context.
     *
     * @param key   the parameter key
     * @param value the parameter value
     */
    public void put(Object key, Object value) {
        getContextMap().put(key, value);
    }
}
