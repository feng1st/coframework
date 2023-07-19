package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;

public interface Node {

    boolean execute(Context<?> context, Logger logger);
}
