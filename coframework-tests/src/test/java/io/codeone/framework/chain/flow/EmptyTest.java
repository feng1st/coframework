package io.codeone.framework.chain.flow;

import io.codeone.framework.chain.context.Context;
import org.junit.jupiter.api.Test;

class EmptyTest {

    @Test
    public void run() {
        Empty.of().run(Context.of());
    }

    @Test
    public void execute() {
        Empty.of().execute(Context.of());
    }
}