package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.context.Context;
import org.springframework.stereotype.Component;

@Component
public class ContinuousTestNode implements Continuous {

    @Override
    public void executeAndContinue(Context context) {
        context.put("key", "value");
    }
}
