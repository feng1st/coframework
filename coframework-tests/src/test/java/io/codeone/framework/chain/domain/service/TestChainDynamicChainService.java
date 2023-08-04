package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.node.Stage1;
import io.codeone.framework.chain.domain.node.Stage2;
import io.codeone.framework.chain.domain.render.TestChainCountRenderer;
import io.codeone.framework.chain.domain.render.TestChainExt1Renderer;
import io.codeone.framework.chain.domain.render.TestChainExt2Renderer;
import io.codeone.framework.chain.extension.ChainDecorator;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestChainDynamicChainService {

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.DYNAMIC,
            Path.of(Stage1.class, Stage2.class, TestChainCountRenderer.class));

    @Resource
    private ChainFactory chainFactory;

    public KeyMap getDataExt1() {
        ChainDecorator chainDecorator = new ChainDecorator();
        applyExt1(chainDecorator);
        return getData(chainDecorator);
    }

    public KeyMap getDataExt1n2() {
        ChainDecorator chainDecorator = new ChainDecorator();
        applyExt1(chainDecorator);
        applyExt2(chainDecorator);
        return getData(chainDecorator);
    }

    private KeyMap getData(ChainDecorator chainDecorator) {
        Chain<KeyMap> chain = chainFactory.getChain(CHAIN_SPEC, chainDecorator);

        Context<KeyMap> context = Context.of(new KeyMap());

        return chain.execute(context, chainDecorator);
    }

    /**
     * This operation could be in an {@link Ability} or an
     * {@link ExtensionPoint}.
     */
    private void applyExt1(ChainDecorator chainDecorator) {
        chainDecorator
                // Adds node TestChainExt1Render prior to Stage1,
                .addPath(Path.of(TestChainExt1Renderer.class, Stage1.class))
                // and its args.
                .addArgument(TestKeys.EXT1_PARAM, "foo");
    }

    private void applyExt2(ChainDecorator chainDecorator) {
        chainDecorator
                // Adds node TestChainExt2Render between Stage1 and Stage2,
                .addPath(Path.of(Stage1.class, TestChainExt2Renderer.class, Stage2.class))
                // and its args.
                .addArgument(TestKeys.EXT2_PARAM, "bar");
    }
}
