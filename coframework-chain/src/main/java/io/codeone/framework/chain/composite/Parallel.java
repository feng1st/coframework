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

public interface Parallel extends Chainable, Composite, Quiet {

    static Parallel of(Chainable... components) {
        return new PlainParallel(components);
    }

    @Override
    @SneakyThrows
    default boolean execute(Context context) {
        ExecutorService threadPool = context.threadPool();
        // execute in parallel
        if (threadPool != null
                && !threadPool.isShutdown()) {
            List<Future<Boolean>> futures = new ArrayList<>();
            for (Chainable component : getComponents()) {
                futures.add(threadPool.submit(() -> component.run(context)));
            }
            for (Future<Boolean> future : futures) {
                if (!future.get()) {
                    // break chain earlier
                    return false;
                }
            }
        }
        // fallback to sequential
        else {
            for (Chainable component : getComponents()) {
                if (!component.run(context)) {
                    // break chain earlier
                    return false;
                }
            }
        }
        // continue chain
        return true;
    }

    @RequiredArgsConstructor
    @Getter
    class PlainParallel implements Parallel {

        private final Chainable[] components;
    }
}
