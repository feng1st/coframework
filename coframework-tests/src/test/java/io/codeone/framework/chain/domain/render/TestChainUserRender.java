package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.GenericData;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainUserRender extends TargetRenderer<GenericData> {

    @Override
    protected GenericData render(GenericData target, Context<?> context, Logger logger) {
        target.copyFromParameter(context, TestKey.USER);
        User user = target.getData(TestKey.USER);
        logger.log(TestKey.USER, (user == null) ? null : user.getId());
        return target;
    }
}
