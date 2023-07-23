package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetProcessor;
import lombok.SneakyThrows;

import java.util.List;

public abstract class BaseTestChainAsyncParallelProcessor extends TargetProcessor<Data> {

    @Override
    @SneakyThrows(InterruptedException.class)
    protected boolean process(Data target, Context<?> context, Logger logger) {
        final int delta = getDelta();

        int i = context.getArgument(TestKey.ASYNC_INDEX);
        if ((i % 2) == (delta % 2)) {
            Thread.sleep(i * 10L);
        } else {
            Thread.sleep(i * 5L);
        }

        target.updateIfPresent(TestKey.ASYNC_SUM, o -> {
            int sum = (int) o + delta;
            logger.log(TestKey.ASYNC_SUM, sum);
            return sum;
        });
        target.updateIfPresent(TestKey.ASYNC_LIST, o -> {
            List<Integer> list = (List<Integer>) o;
            list.add(delta);
            logger.log(TestKey.ASYNC_LIST, list);
            return list;
        });

        return false;
    }

    protected abstract int getDelta();
}
