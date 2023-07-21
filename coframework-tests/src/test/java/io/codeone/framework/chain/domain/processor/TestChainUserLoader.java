package io.codeone.framework.chain.domain.processor;

import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.ContextProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestChainUserLoader extends ContextProcessor {

    @Override
    protected boolean process(Context<?> context, Logger logger) {
        Long userId = context.getArgument(TestKey.USER_ID);
        logger.log(TestKey.USER_ID, userId);
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        context.setArgumentIfAbsent(TestKey.USER, () -> loadUser(userId));
        User user = context.getArgument(TestKey.USER);
        logger.log(TestKey.USER, (user == null) ? null : user.getName());
        return false;
    }

    private User loadUser(long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("MockUser" + userId);
        return user;
    }
}
