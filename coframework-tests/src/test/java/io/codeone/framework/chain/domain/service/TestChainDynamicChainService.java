package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.render.TestChainCountRender;
import io.codeone.framework.chain.domain.render.TestChainExt1Render;
import io.codeone.framework.chain.domain.render.TestChainExt2Render;
import io.codeone.framework.chain.extension.Interference;
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
            Path.of(TestChainCountRender.class));

    @Resource
    private ChainFactory chainFactory;

    public Data getDataExt1() {
        Interference interference = new Interference();
        applyExt1(interference);
        return getData(interference);
    }

    public Data getDataExt1n2() {
        Interference interference = new Interference();
        applyExt1(interference);
        applyExt2(interference);
        return getData(interference);
    }

    private Data getData(Interference interference) {
        Chain<Data> chain = chainFactory.getChain(interference.interfere(CHAIN_SPEC));

        Context<Data> context = interference.interfere(Context.of(Data.of()));

        return chain.execute(context);
    }

    /**
     * This operation could be in an {@link Ability} or an
     * {@link ExtensionPoint}.
     */
    private void applyExt1(Interference interference) {
        interference
                // Adds node TestChainExt1Render prior to TestChainCountRender,
                .addPath(Path.of(TestChainExt1Render.class, TestChainCountRender.class))
                // and its args.
                .addArgument(TestKey.EXT1_PARAM, "foo");
    }

    private void applyExt2(Interference interference) {
        interference
                // Adds node TestChainExt2Render prior to TestChainCountRender,
                .addPath(Path.of(TestChainExt2Render.class, TestChainCountRender.class))
                // and its args.
                .addArgument(TestKey.EXT2_PARAM, "bar");
    }
}
