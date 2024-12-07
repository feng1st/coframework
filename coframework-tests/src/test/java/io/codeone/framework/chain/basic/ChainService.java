package io.codeone.framework.chain.basic;

import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChainService {
    @Autowired
    private Produce produce;
    @Autowired
    private Consume consume;

    public void run() {
        Sequential.of(produce, consume)
                .run(Context.of());
    }
}
