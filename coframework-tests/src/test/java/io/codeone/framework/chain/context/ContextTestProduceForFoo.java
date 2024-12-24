package io.codeone.framework.chain.context;

import io.codeone.framework.ext.annotation.Extension;

@Extension(bizId = "foo")
public class ContextTestProduceForFoo implements ContextTestProduce {

    @Override
    public void executeAndContinue(Context context) {
        context.put(String.class, "foo");
    }
}
