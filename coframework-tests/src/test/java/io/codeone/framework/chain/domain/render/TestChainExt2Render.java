package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainExt2Render extends TargetRenderer<Data> {

    @Override
    protected Data render(Data target, Context<?> context, Logger logger) {
        target.set(TestKey.EXT2_RESULT, "ext2: " + context.getArgument(TestKey.EXT2_PARAM));
        logger.log(TestKey.EXT2_RESULT, target.get(TestKey.EXT2_RESULT));
        return target;
    }
}
