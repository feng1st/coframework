package io.codeone.framework.chain.domain.service;

import io.codeone.framework.chain.Chain;
import io.codeone.framework.chain.ChainFactory;
import io.codeone.framework.chain.ChainSpec;
import io.codeone.framework.chain.domain.constants.TestKey;
import io.codeone.framework.chain.domain.constants.TestNames;
import io.codeone.framework.chain.domain.processor.TestChainAsyncMapper;
import io.codeone.framework.chain.domain.processor.TestChainAsyncParallelAProcessor;
import io.codeone.framework.chain.domain.processor.TestChainAsyncParallelBProcessor;
import io.codeone.framework.chain.domain.processor.TestChainAsyncReducer;
import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.model.Data;
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

    public Data getData(int i) {
        Chain<Data> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<Data> context = Context.of(Data.of())
                .setArgument(TestKey.ASYNC_INDEX, i);

        return chain.execute(context);
    }

    public Data getDataAsync(int i) throws InterruptedException {
        Chain<Data> chain = chainFactory.getChain(CHAIN_SPEC);

        Context<Data> context = Context.of(Data.of())
                .setArgument(TestKey.ASYNC_INDEX, i);

        return chain.executeAsync(context, executor);
    }
}
