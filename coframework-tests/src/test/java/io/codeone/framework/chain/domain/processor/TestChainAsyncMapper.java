package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetProcessor;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TestChainAsyncMapper extends TargetProcessor<KeyMap> {

    @Override
    protected boolean process(KeyMap target, Context<?> context, Logger logger) {
        target.put(TestKeys.ASYNC_SUM, 0);
        target.put(TestKeys.ASYNC_LIST, new ArrayList<>());
        return false;
    }
}
