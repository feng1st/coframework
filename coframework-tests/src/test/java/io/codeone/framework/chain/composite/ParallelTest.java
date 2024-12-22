package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.context.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

class ParallelTest {

    @Test
    public void asSequential() {
        Assertions.assertEquals(Arrays.asList(1, 2), Parallel.of(context -> {
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    return true;
                }).run(Context.of("key", new ArrayList<>()), "key"));
    }

    @Test
    public void asSequentialThreadPoolShutdown() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        threadPool.shutdown();
        Assertions.assertEquals(Arrays.asList(1, 2), Parallel.of(context -> {
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    return true;
                }).run(Context.of("key", new ArrayList<>()).threadPool(threadPool), "key"));
    }

    @Test
    public void asSequentialBreak() {
        Assertions.assertEquals(Arrays.asList(1, 2), Parallel.of(context -> {
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    return false;
                },
                context -> {
                    context.<List<Integer>>get("key").add(3);
                    return true;
                }).run(Context.of("key", new ArrayList<>()), "key"));
    }

    @Test
    public void parallel() {
        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertEquals(Arrays.asList(2, 1), Parallel.of(context -> {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    context.<List<Integer>>get("key").add(1);
                    return true;
                },
                context -> {
                    context.<List<Integer>>get("key").add(2);
                    latch.countDown();
                    return true;
                }).run(Context.of("key", new ArrayList<>()).threadPool(ForkJoinPool.commonPool()), "key"));
    }

    @Test
    public void parallelBreak() {
        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertEquals(Arrays.asList(2, 1), Sequential.of(
                Parallel.of(context -> {
                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            context.<List<Integer>>get("key").add(1);
                            return true;
                        },
                        context -> {
                            context.<List<Integer>>get("key").add(2);
                            latch.countDown();
                            return false;
                        }),
                context -> {
                    context.<List<Integer>>get("key").add(3);
                    return true;
                }).run(Context.of("key", new ArrayList<>()).threadPool(ForkJoinPool.commonPool()), "key"));
    }
}