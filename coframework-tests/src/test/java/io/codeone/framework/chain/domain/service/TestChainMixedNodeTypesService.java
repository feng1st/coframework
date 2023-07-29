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
import io.codeone.framework.chain.model.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainMixedNodeTypesService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.MIXED, Path.of(
            // ContextProcessor
            TestChainUserLoader.class,
            // ContextProcessor
            TestChainUserExtraInfoLoader.class,
            // TargetRenderer<Data>
            TestChainUserRenderer.class,
            // TargetRenderer<Data>
            TestChainCountRenderer.class,
            // TargetProcessor<Data>
            TestChainCounter.class));

    @Resource
    private ChainFactory chainFactory;

    public Data getData(long userId) {
        Chain<Data> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<Data> context = Context.of(Data.of())
                .setArgument(TestKeys.USER_ID, userId);

        return chain.execute(context);
    }
}
