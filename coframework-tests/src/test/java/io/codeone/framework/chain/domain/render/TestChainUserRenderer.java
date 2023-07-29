package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.chain.node.TargetRenderer;
import org.springframework.stereotype.Component;

@Component
public class TestChainUserRenderer extends TargetRenderer<Data> {

    @Override
    protected Data render(Data target, Context<?> context, Logger logger) {
        target.copyFromParameter(context, TestKeys.USER);
        User user = target.get(TestKeys.USER);
        logger.log(TestKeys.USER, (user == null) ? null : user.getId());
        return target;
    }
}
