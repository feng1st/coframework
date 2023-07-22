package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TestChainAsyncMapper extends TargetProcessor<Data> {

    @Override
    protected boolean process(Data target, Context<?> context, Logger logger) {
        target.set(TestKey.ASYNC_SUM, 0);
        target.set(TestKey.ASYNC_LIST, new ArrayList<>());
        return false;
    }
}
