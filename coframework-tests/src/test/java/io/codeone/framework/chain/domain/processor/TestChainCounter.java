package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetProcessor;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Component;

@Component
public class TestChainCounter extends TargetProcessor<KeyMap> {

    @Override
    protected boolean process(KeyMap target, Context<?> context, Logger logger) {
        target.updateIfPresent(TestKeys.COUNT, count -> (int) count + 1);
        logger.log(TestKeys.COUNT, target.get(TestKeys.COUNT));
        return false;
    }
}
