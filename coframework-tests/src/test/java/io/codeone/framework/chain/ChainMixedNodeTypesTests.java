package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainMixedNodeTypesService;
import io.codeone.framework.chain.model.GenericData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ChainMixedNodeTypesTests {

    @Resource
    private TestChainMixedNodeTypesService testChainMixedNodeTypesService;

    @Test
    void mixedNodeTypes() {
        GenericData data = testChainMixedNodeTypesService.getData(1L);
        Assertions.assertEquals(
                "GenericData(data={TestKey.USER=User(id=1, name=MockUser1, extraInfo=extra info of user1), TestKey.COUNT=2})",
                data.toString());
    }
}
