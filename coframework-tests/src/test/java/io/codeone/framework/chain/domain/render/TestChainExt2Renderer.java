package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainExt2Renderer extends TargetRenderer<Data> {

    @Override
    protected Data render(Data target, Context<?> context, Logger logger) {
        target.set(TestKeys.EXT2_RESULT, "ext2: " + context.getArgument(TestKeys.EXT2_PARAM));
        logger.log(TestKeys.EXT2_RESULT, target.get(TestKeys.EXT2_RESULT));
        return target;
    }
}
