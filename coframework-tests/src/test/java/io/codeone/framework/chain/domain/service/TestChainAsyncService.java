package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.domain.constants.TestKeys;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.processor.TestChainAsyncMapper;
import io.codeone.framework.chain.domain.processor.TestChainAsyncParallelAProcessor;
import io.codeone.framework.chain.domain.processor.TestChainAsyncParallelBProcessor;
import io.codeone.framework.chain.domain.processor.TestChainAsyncReducer;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.model.KeyMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TestChainAsyncService {

    private final Executor executor = Executors.newCachedThreadPool();

    private static final ChainSpec CHAIN_SPEC = ChainSpec.of(TestNames.ASYNC,
            // Two paths, with the same starting and ending node:
            // Mapper -> ParallelA -> Reducer
            // Mapper -> ParallelB -> Reducer
            Path.of(TestChainAsyncMapper.class,
                    TestChainAsyncParallelAProcessor.class,
                    TestChainAsyncReducer.class),
            Path.of(TestChainAsyncMapper.class,
                    TestChainAsyncParallelBProcessor.class,
                    TestChainAsyncReducer.class));

    @Resource
    private ChainFactory chainFactory;

    public KeyMap getData(int i) {
        Chain<KeyMap> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<KeyMap> context = Context.of(new KeyMap())
                .setArgument(TestKeys.ASYNC_INDEX, i);

        return chain.execute(context);
    }

    public KeyMap getDataAsync(int i) throws InterruptedException {
        Chain<KeyMap> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<KeyMap> context = Context.of(new KeyMap())
                .setArgument(TestKeys.ASYNC_INDEX, i);

        return chain.executeAsync(context, executor);
    }
}
