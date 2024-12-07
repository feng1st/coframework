package io.codeone.framework.chain.basic;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import org.springframework.stereotype.Component;

@Component
public class Consume implements Chainable {
    @Override
    public boolean execute(Context context) {
        assert "content".equals(context.get(String.class));
        return true;
    }
}
