package io.codeone.framework.chain.domain.processor;

import org.springframework.stereotype.Component;

@Component
public class TestChainAsyncParallelAProcessor extends BaseTestChainAsyncParallelProcessor {

    @Override
    protected int getDelta() {
        return 1;
    }
}
