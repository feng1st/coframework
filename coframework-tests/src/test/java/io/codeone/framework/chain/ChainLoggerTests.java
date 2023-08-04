package io.codeone.framework.chain;

import io.codeone.framework.chain.domain.service.TestChainLoggerService;
import io.codeone.framework.logging.BaseLogTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
public class ChainLoggerTests extends BaseLogTests {

    @Resource
    private TestChainLoggerService testChainLoggerService;

    @Test
    void testLogger() {
        testChainLoggerService.executeChain(1L);

        assertLogs(Arrays.asList(
                "||level=>INFO||scene=>LOGGER||success=>true||code=>SUCCESS||elapsed=>0||arg.target=>KeyMap(data={})||arg.USER_ID=>1",
                "||level=>INFO||scene=>LOGGER||method=>TestChainUserLoader||success=>true||code=>SUCCESS||elapsed=>0||arg.USER_ID=>1||arg.USER=>MockUser1||result=>false",
                "||level=>INFO||scene=>LOGGER||method=>TestChainUserExtraInfoLoader||success=>true||code=>SUCCESS||elapsed=>0||arg.USER_ID=>1||arg.USER=>MockUser1||arg.user.extraInfo=>extra info of user1||result=>false",
                "||level=>INFO||scene=>LOGGER||method=>TestChainCountRenderer||success=>true||code=>SUCCESS||elapsed=>0||arg.USER_ID=>1||arg.USER=>MockUser1||arg.COUNT=>1||result=>false",
                "||level=>INFO||scene=>LOGGER||method=>TestChainCounter||success=>true||code=>SUCCESS||elapsed=>0||arg.USER_ID=>1||arg.USER=>MockUser1||arg.COUNT=>2||result=>false"));
    }
}
