package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainDerivedChainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
public class ChainDerivedChainTests {

    @Resource
    private TestChainDerivedChainService testChainDerivedChainService;

    @Test
    void basicChain() {
        List<Long> target = LongStream.rangeClosed(1L, 10L).boxed()
                .collect(Collectors.toList());
        List<Long> result = testChainDerivedChainService.filterLongsBasicChain(target);
        Assertions.assertEquals(Arrays.asList(1L, 3L, 7L, 9L), result);
    }

    @Test
    void derivedChain() {
        List<Long> target = LongStream.rangeClosed(1L, 10L).boxed()
                .collect(Collectors.toList());
        List<Long> result = testChainDerivedChainService.filterLongsDerivedChain(target);
        // The derived chain has an additional node compared to the basic
        // chain, which filtered out the 3 and 9.
        Assertions.assertEquals(Arrays.asList(1L, 7L), result);
    }
}
