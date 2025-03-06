package io.codeone.framework.chain.composite;

import io.codeone.framework.chain.context.Context;

public class SequentialTestSubclassComponentImpl implements SequentialTestSubclassComponent {

    @Override
    public void doStuff(Context context) {
        context.put("stuffDone", true);
    }
}
