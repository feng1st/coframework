package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.Quiet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Represents a chainable unit that executes its components in parallel.
 *
 * <p>Components are executed concurrently if a thread pool is available. If any
 * component returns {@code false}, the chain stops, and the execution short-circuits.
 */
public interface Parallel extends Chainable, Composite, Quiet {

    /**
     * Creates a new {@code Parallel} instance with the specified components.
     *
     * @param components the components to be executed in parallel
     * @return a new {@code Parallel} instance
     */
    static Parallel of(Chainable... components) {
        return new PlainParallel(components);
    }

    /**
     * Executes all components in parallel using the provided thread pool, or sequentially
     * if no thread pool is available.
     *
     * @param context the context in which execution occurs
     * @return {@code true} if all components succeed; {@code false} otherwise
     */
    @Override
    @SneakyThrows
    default boolean execute(Context context) {
        ExecutorService threadPool = context.threadPool();
        // Execute in parallel
        if (threadPool != null
                && !threadPool.isShutdown()) {
            List<Future<Boolean>> futures = new ArrayList<>();
            for (Chainable component : getComponents()) {
                futures.add(threadPool.submit(() -> component.run(context)));
            }
            boolean toContinue = true;
            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    // Break chain early
                    toContinue = false;
                }
            }
            return toContinue;
        }
        // Fallback to sequential execution
        else {
            for (Chainable component : getComponents()) {
                if (!component.run(context)) {
                    // Break chain early
                    return false;
                }
            }
        }
        // Continue chain
        return true;
    }

    /**
     * A plain implementation of {@link Parallel}.
     */
    @RequiredArgsConstructor
    @Getter
    class PlainParallel implements Parallel {

        /**
         * The components to be executed in parallel.
         */
        private final Chainable[] components;
    }
}
