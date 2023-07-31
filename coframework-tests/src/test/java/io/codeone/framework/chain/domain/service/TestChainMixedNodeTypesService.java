package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.processor.TestChainCounter;
import io.codeone.framework.chain.domain.processor.TestChainUserExtraInfoLoader;
import io.codeone.framework.chain.domain.processor.TestChainUserLoader;
import io.codeone.framework.chain.domain.render.TestChainCountRenderer;
import io.codeone.framework.chain.domain.render.TestChainUserRenderer;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainMixedNodeTypesService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.MIXED, Path.of(
            // ContextProcessor
            TestChainUserLoader.class,
            // ContextProcessor
            TestChainUserExtraInfoLoader.class,
            // TargetRenderer<KeyMap>
            TestChainUserRenderer.class,
            // TargetRenderer<KeyMap>
            TestChainCountRenderer.class,
            // TargetProcessor<KeyMap>
            TestChainCounter.class));

    @Resource
    private ChainFactory chainFactory;

    public KeyMap getData(long userId) {
        Chain<KeyMap> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<KeyMap> context = Context.of(new KeyMap())
                .setArgument(TestKeys.USER_ID, userId);

        return chain.execute(context);
    }
}
