package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainAsyncService;
import io.codeone.framework.chain.model.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class ChainAsyncTests {

    @Resource
    private TestChainAsyncService testChainAsyncService;

    @Test
    void sync() {
        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(testChainAsyncService.getData(i));
        }
        Set<String> set = dataList.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        Assertions.assertEquals(1, set.size());
        Assertions.assertTrue(set.contains("Data(data={TestKey.ASYNC_LIST=[1, 2, 100], TestKey.ASYNC_SUM=103})")
                || set.contains("Data(data={TestKey.ASYNC_LIST=[2, 1, 100], TestKey.ASYNC_SUM=103})"));
    }

    @Test
    void async() throws Exception {
        List<Data> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(testChainAsyncService.getDataAsync(i));
        }
        Set<String> set = dataList.stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        Assertions.assertEquals(2, set.size());
        Assertions.assertTrue(set.contains("Data(data={TestKey.ASYNC_LIST=[1, 2, 100], TestKey.ASYNC_SUM=103})")
                && set.contains("Data(data={TestKey.ASYNC_LIST=[2, 1, 100], TestKey.ASYNC_SUM=103})"));
    }
}
