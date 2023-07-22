package io.codeone.framework.chain;

import io.codeone.framework.chain.dag.Path;
import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.render.TestChainCountRender;
import io.codeone.framework.chain.domain.render.TestChainExt1Render;
import io.codeone.framework.chain.domain.render.TestChainExt2Render;
import io.codeone.framework.chain.domain.service.TestChainDynamicChainService;
import io.codeone.framework.chain.extension.Interference;
import io.codeone.framework.chain.model.Data;
import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ChainDynamicChainTests {

    @Resource
    private TestChainDynamicChainService testChainDynamicChainService;

    @Test
    void onlyExt1() {
        Interference interference = new Interference();
        applyExt1(interference);
        Data data = testChainDynamicChainService.getData(interference);
        // Extended by ext1.
        Assertions.assertEquals("Data(data={TestKey.EXT1_RESULT=ext1: foo, TestKey.COUNT=1})", data.toString());
    }

    @Test
    void ext1n2() {
        Interference interference = new Interference();
        applyExt1(interference);
        applyExt2(interference);
        Data data = testChainDynamicChainService.getData(interference);
        // Extended by ext1 and ext2.
        Assertions.assertEquals(
                "Data(data={TestKey.EXT2_RESULT=ext2: bar, TestKey.EXT1_RESULT=ext1: foo, TestKey.COUNT=1})",
                data.toString());
    }

    /**
     * This operation could be in an {@link Ability} or an
     * {@link ExtensionPoint}.
     */
    private void applyExt1(Interference interference) {
        interference
                // Adds a node,
                .addPath(Path.of(TestChainExt1Render.class, TestChainCountRender.class))
                // and its args.
                .addArgument(TestKey.EXT1_PARAM, "foo");
    }

    private void applyExt2(Interference interference) {
        interference
                // Adds a node,
                .addPath(Path.of(TestChainExt2Render.class, TestChainCountRender.class))
                // and its args.
                .addArgument(TestKey.EXT2_PARAM, "bar");
    }
}
