package io.codeone.framework.chain.basic;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChainBasicTests {

    @Autowired
    private ChainService chainService;

    @Test
    void test() {
        chainService.run();
    }
}
