package io.codeone.framework.chain.log;

import io.codeone.framework.chain.context.Context;
import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "bar")
public class ChainLoggerTestNodeAbilityForBar implements ChainLoggerTestNodeAbility {

    @Override
    public void executeAndContinue(Context context) {
        context.put(String.class, "bar");
    }
}
