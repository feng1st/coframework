package io.codeone.framework.chain.basic;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import org.springframework.stereotype.Component;

@Component
public class Write implements Chainable {
    @Override
    public boolean execute(Context context) {
        context.put(String.class, "test");
        return true;
    }
}
