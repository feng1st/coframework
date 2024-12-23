package io.codeone.framework.chain.log;

import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.flow.Continuous;
import org.springframework.stereotype.Component;

@Component
public class ChainLoggerTestNodeProduce implements Continuous {

    @Override
    public void executeAndContinue(Context context) {
        context.put(String.class, "Hello chain");
    }
}
