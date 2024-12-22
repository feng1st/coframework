package io.codeone.framework.chain;

import io.codeone.framework.chain.composite.Parallel;
import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.shared.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ForkJoinPool;

@SpringBootTest
class ChainableTest {

    @Autowired
    private MapProduceFoo mapProduceFoo;
    @Autowired
    private MapProduceBar mapProduceBar;
    @Autowired
    private ReduceProduce reduceProduce;
    @Autowired
    private MapConsumeFoo mapConsumeFoo;
    @Autowired
    private MapConsumeBar mapConsumeBar;
    @Autowired
    private ReduceConsume reduceConsume;

    @Test
    public void lambda() {
        Sequential.of(context -> {
                    context.put("key", 1);
                    return true;
                },
                context -> {
                    Assertions.assertEquals(1, context.<Integer>get("key"));
                    return true;
                }).run(Context.of());
    }

    @Test
    public void sequential() {
        Sequential.of(
                Parallel.of(
                        mapProduceFoo,
                        mapProduceBar),
                reduceProduce,
                Parallel.of(
                        mapConsumeFoo,
                        mapConsumeBar),
                reduceConsume).run(Context.of()
                .threadPool(ForkJoinPool.commonPool()));
    }
}