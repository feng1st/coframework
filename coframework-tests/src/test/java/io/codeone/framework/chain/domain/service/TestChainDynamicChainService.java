package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.dag.Path;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.render.TestChainCountRender;
import io.codeone.framework.chain.extension.Interference;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainDynamicChainService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.DYNAMIC,
            Path.of(TestChainCountRender.class));

    @Resource
    private ChainFactory chainFactory;

    public Data getData(Interference interference) {
        ChainSpec chainSpec = interference.interfere(CHAIN_SPEC);

        Chain<Data> chain = chainFactory.getChain(chainSpec);

        Context<Data> context = Context.of(Data.of());

        interference.interfere(context);

        return chain.execute(context);
    }
}
