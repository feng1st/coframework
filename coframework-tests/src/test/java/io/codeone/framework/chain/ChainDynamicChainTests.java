package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainDynamicChainService;
import io.codeone.framework.model.KeyMap;
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
        KeyMap keyMap = testChainDynamicChainService.getDataExt1();
        // Extended by ext1.
        Assertions.assertEquals("KeyMap(content={TestKeys.EXT1_RESULT=ext1: foo, TestKeys.COUNT=1})",
                keyMap.toString());
    }

    @Test
    void ext1n2() {
        KeyMap keyMap = testChainDynamicChainService.getDataExt1n2();
        // Extended by ext1 and ext2.
        Assertions.assertEquals(
                "KeyMap(content={TestKeys.EXT1_RESULT=ext1: foo, TestKeys.EXT2_RESULT=ext2: bar, TestKeys.COUNT=1})",
                keyMap.toString());
    }
}
