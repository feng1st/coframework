package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.GenericData;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainCountRender extends TargetRenderer<GenericData> {

    @Override
    protected GenericData render(GenericData target, Context<?> context, Logger logger) {
        target.setData(TestKey.COUNT, 1);
        logger.log(TestKey.COUNT, target.getData(TestKey.COUNT));
        return target;
    }
}
