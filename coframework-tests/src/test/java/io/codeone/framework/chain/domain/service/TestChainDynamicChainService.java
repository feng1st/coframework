package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.domain.render.TestChainCountRender;
import io.codeone.framework.chain.extension.Interference;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.GenericData;
import io.codeone.framework.chain.spec.BasicChainSpec;
import io.codeone.framework.chain.spec.ChainSpec;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainDynamicChainService {

    private static final ChainSpec CHAIN_SPEC = BasicChainSpec.of("testDynamic",
            TestChainCountRender.class);

    @Resource
    private ChainFactory chainFactory;

    public GenericData getData(Interference interference) {
        ChainSpec chainSpec = interference.interfere(CHAIN_SPEC);

        Chain<GenericData> chain = chainFactory.getChain(chainSpec);

        Context<GenericData> context = Context.of(GenericData.of());

        interference.interfere(context);

        return chain.execute(context);
    }
}
