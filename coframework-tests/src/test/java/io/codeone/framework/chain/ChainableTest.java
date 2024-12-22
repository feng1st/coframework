package io.codeone.framework.chain;

import io.codeone.framework.chain.composite.Parallel;
import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.shared.*;
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
    private MapProduceBaz mapProduceBaz;
    @Autowired
    private ReduceProduce reduceProduce;
    @Autowired
    private MapConsumeFoo mapConsumeFoo;
    @Autowired
    private MapConsumeBar mapConsumeBar;
    @Autowired
    private MapConsumeBaz mapConsumeBaz;
    @Autowired
    private ReduceConsume reduceConsume;

    @Test
    public void sequential() {
        Sequential.of(
                Parallel.of(
                        mapProduceFoo,
                        mapProduceBar,
                        mapProduceBaz),
                reduceProduce,
                Parallel.of(
                        mapConsumeFoo,
                        mapConsumeBar,
                        mapConsumeBaz),
                reduceConsume).run(Context.of().threadPool(ForkJoinPool.commonPool()));
    }
}