package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.GenericData;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainExt1Render extends TargetRenderer<GenericData> {

    @Override
    protected GenericData render(GenericData target, Context<?> context, Logger logger) {
        target.setData(TestKey.EXT1_RESULT, "ext1: " + context.getArgument(TestKey.EXT1_PARAM));
        logger.log(TestKey.EXT1_RESULT, target.getData(TestKey.EXT1_RESULT));
        return target;
    }
}
