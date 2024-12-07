package io.codeone.framework.chain.basic;

import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChainService {
    @Autowired
    private Write write;
    @Autowired
    private Read read;

    public void run() {
        Sequential.of(write, read)
                .run(Context.of());
    }
}
