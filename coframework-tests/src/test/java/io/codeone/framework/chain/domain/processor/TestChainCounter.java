package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.GenericData;
import io.codeone.framework.chain.node.TargetProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestChainCounter extends TargetProcessor<GenericData> {

    @Override
    protected boolean process(GenericData target, Context<?> context, Logger logger) {
        target.updateData(TestKey.COUNT, count -> (int) count + 1);
        logger.log(TestKey.COUNT, target.getData(TestKey.COUNT));
        return false;
    }
}
