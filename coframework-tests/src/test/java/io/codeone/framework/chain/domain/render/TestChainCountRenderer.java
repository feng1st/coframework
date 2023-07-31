package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetRenderer;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Component;

@Component
public class TestChainCountRenderer extends TargetRenderer<KeyMap> {

    @Override
    protected KeyMap render(KeyMap target, Context<?> context, Logger logger) {
        target.put(TestKeys.COUNT, 1);
        logger.log(TestKeys.COUNT, target.get(TestKeys.COUNT));
        return target;
    }
}
