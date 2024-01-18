package io.codeone.framework.chain;

import io.codeone.framework.chain.chainable.BaseChainSpec;
import io.codeone.framework.chain.chainable.Chainable;

public final class ChainSpec extends BaseChainSpec {

    public static ChainSpec of(Chainable first, Chainable... rest) {
        return new ChainSpec(first, rest);
    }

    private ChainSpec(Chainable first, Chainable... rest) {
        super(first, rest);
    }
}
