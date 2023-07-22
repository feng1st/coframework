package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainDynamicChainService;
import io.codeone.framework.chain.model.Data;
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
        Data data = testChainDynamicChainService.getDataExt1();
        // Extended by ext1.
        Assertions.assertEquals("Data(data={TestKey.EXT1_RESULT=ext1: foo, TestKey.COUNT=1})", data.toString());
    }

    @Test
    void ext1n2() {
        Data data = testChainDynamicChainService.getDataExt1n2();
        // Extended by ext1 and ext2.
        Assertions.assertEquals(
                "Data(data={TestKey.EXT2_RESULT=ext2: bar, TestKey.EXT1_RESULT=ext1: foo, TestKey.COUNT=1})",
                data.toString());
    }
}
