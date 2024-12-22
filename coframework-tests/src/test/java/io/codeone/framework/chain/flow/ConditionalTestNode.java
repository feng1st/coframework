package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.context.Context;
import org.springframework.stereotype.Component;

@Component
public class ConditionalTestNode implements Conditional {

    @Override
    public boolean test(Context context) {
        return context.get("test");
    }

    @Override
    public void executeIfTrueAndContinue(Context context) {
        context.put("true", true);
        Conditional.super.executeIfTrueAndContinue(context);
    }

    @Override
    public void executeIfFalseAndContinue(Context context) {
        context.put("false", true);
        Conditional.super.executeIfFalseAndContinue(context);
    }
}
