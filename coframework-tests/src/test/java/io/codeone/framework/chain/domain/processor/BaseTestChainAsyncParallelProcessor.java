package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetProcessor;
import io.codeone.framework.model.KeyMap;
import lombok.SneakyThrows;

import java.util.List;

public abstract class BaseTestChainAsyncParallelProcessor extends TargetProcessor<KeyMap> {

    @Override
    @SneakyThrows(InterruptedException.class)
    protected boolean process(KeyMap target, Context<?> context, Logger logger) {
        final int delta = getDelta();

        int i = context.getArgument(TestKeys.ASYNC_INDEX);
        if ((i % 2) == (delta % 2)) {
            Thread.sleep(i * 10L);
        } else {
            Thread.sleep(i * 5L);
        }

        target.updateIfPresent(TestKeys.ASYNC_SUM, o -> {
            int sum = (int) o + delta;
            logger.log(TestKeys.ASYNC_SUM, sum);
            return sum;
        });
        target.updateIfPresent(TestKeys.ASYNC_LIST, o -> {
            List<Integer> list = (List<Integer>) o;
            list.add(delta);
            logger.log(TestKeys.ASYNC_LIST, list);
            return list;
        });

        return false;
    }

    protected abstract int getDelta();
}
