package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.domain.constants.TestChains;
import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.chain.domain.processor.TestChainCounter;
import io.codeone.framework.chain.domain.processor.TestChainUserExtraInfoLoader;
import io.codeone.framework.chain.domain.processor.TestChainUserLoader;
import io.codeone.framework.chain.domain.render.TestChainCountRenderer;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainLoggerService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestChains.LOGGER, Path.of(
            TestChainUserLoader.class,
            TestChainUserExtraInfoLoader.class,
            TestChainCountRenderer.class,
            TestChainCounter.class));

    @Resource
    private ChainFactory chainFactory;

    public void executeChain(long userId) {
        Chain<KeyMap> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<KeyMap> context = Context.<KeyMap>newBuilder()
                .withTarget(new KeyMap())
                .withNodeLogger(this::logEveryNode)
                .build()
                .setArgument(TestKeys.USER_ID, userId);

        chain.execute(context);
    }

    private void logEveryNode(Context<KeyMap> context, Logger logger) {
        context.ifArgumentPresent(TestKeys.USER_ID, o -> logger.log(TestKeys.USER_ID, o));
        context.ifArgumentPresent(TestKeys.USER, o -> logger.log(TestKeys.USER, ((User) o).getName()));
    }
}
