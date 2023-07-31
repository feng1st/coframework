package io.codeone.framework.chain.domain.render;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetRenderer;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Component;

@Component
public class TestChainUserRenderer extends TargetRenderer<KeyMap> {

    @Override
    protected KeyMap render(KeyMap target, Context<?> context, Logger logger) {
        context.copyTo(target, TestKeys.USER);
        User user = target.get(TestKeys.USER);
        logger.log(TestKeys.USER, (user == null) ? null : user.getId());
        return target;
    }
}
