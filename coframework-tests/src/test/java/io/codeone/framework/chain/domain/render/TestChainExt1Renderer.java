package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetRenderer;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Component;

@Component
public class TestChainExt1Renderer extends TargetRenderer<KeyMap> {

    @Override
    protected KeyMap render(KeyMap target, Context<?> context, Logger logger) {
        target.put(TestKeys.EXT1_RESULT, "ext1: " + context.getArgument(TestKeys.EXT1_PARAM));
        logger.log(TestKeys.EXT1_RESULT, target.get(TestKeys.EXT1_RESULT));
        return target;
    }
}
