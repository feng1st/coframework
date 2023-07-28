package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.node.Stage1;
import io.codeone.framework.chain.domain.node.Stage2;
import io.codeone.framework.chain.domain.render.TestChainCountRender;
import io.codeone.framework.chain.domain.render.TestChainExt1Render;
import io.codeone.framework.chain.domain.render.TestChainExt2Render;
import io.codeone.framework.chain.extension.ChainExtension;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainDynamicChainService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.DYNAMIC,
            Path.of(Stage1.class, Stage2.class, TestChainCountRender.class));

    @Resource
    private ChainFactory chainFactory;

    public Data getDataExt1() {
        ChainExtension chainExtension = new ChainExtension();
        applyExt1(chainExtension);
        return getData(chainExtension);
    }

    public Data getDataExt1n2() {
        ChainExtension chainExtension = new ChainExtension();
        applyExt1(chainExtension);
        applyExt2(chainExtension);
        return getData(chainExtension);
    }

    private Data getData(ChainExtension chainExtension) {
        Chain<Data> chain = chainFactory.getChain(CHAIN_SPEC, chainExtension);

        Context<Data> context = Context.of(Data.of());

        return chain.execute(context, chainExtension);
    }

    /**
     * This operation could be in an {@link Ability} or an
     * {@link ExtensionPoint}.
     */
    private void applyExt1(ChainExtension chainExtension) {
        chainExtension
                // Adds node TestChainExt1Render prior to Stage1,
                .addPath(Path.of(TestChainExt1Render.class, Stage1.class))
                // and its args.
                .addArgument(TestKey.EXT1_PARAM, "foo");
    }

    private void applyExt2(ChainExtension chainExtension) {
        chainExtension
                // Adds node TestChainExt2Render between Stage1 and Stage2,
                .addPath(Path.of(Stage1.class, TestChainExt2Render.class, Stage2.class))
                // and its args.
                .addArgument(TestKey.EXT2_PARAM, "bar");
    }
}
