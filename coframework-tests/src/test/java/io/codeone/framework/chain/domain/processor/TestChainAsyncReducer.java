package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestChainAsyncReducer extends TargetProcessor<Data> {

    @Override
    protected boolean process(Data target, Context<?> context, Logger logger) {
        int sum = target.get(TestKey.ASYNC_SUM);
        sum += 100;
        target.set(TestKey.ASYNC_SUM, sum);
        logger.log(TestKey.ASYNC_SUM, target.get(TestKey.ASYNC_SUM));

        List<Integer> list = target.get(TestKey.ASYNC_LIST);
        list.add(100);
        logger.log(TestKey.ASYNC_LIST, target.get(TestKey.ASYNC_LIST));
        return false;
    }
}
