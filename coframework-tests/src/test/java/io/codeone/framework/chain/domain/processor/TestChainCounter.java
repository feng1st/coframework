package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestChainCounter extends TargetProcessor<Data> {

    @Override
    protected boolean process(Data target, Context<?> context, Logger logger) {
        target.updateIfPresent(TestKey.COUNT, count -> (int) count + 1);
        logger.log(TestKey.COUNT, target.get(TestKey.COUNT));
        return false;
    }
}
