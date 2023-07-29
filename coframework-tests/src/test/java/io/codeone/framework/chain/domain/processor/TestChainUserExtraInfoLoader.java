package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.ContextProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestChainUserExtraInfoLoader extends ContextProcessor {

    @Override
    protected boolean process(Context<?> context, Logger logger) {
        User user = context.getArgument(TestKeys.USER);
        logger.log(TestKeys.USER, (user == null) ? null : user.getName());
        if (user == null) {
            throw new IllegalArgumentException();
        }
        loadExtraInfo(user);
        logger.log("user.extraInfo", user.getExtraInfo());
        return false;
    }

    private void loadExtraInfo(User user) {
        user.setExtraInfo("extra info of user" + user.getId());
    }
}
