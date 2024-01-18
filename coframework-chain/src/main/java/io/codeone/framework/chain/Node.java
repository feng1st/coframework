package io.codeone.framework.chain;

import io.codeone.framework.chain.logging.KvLogger;
import io.codeone.framework.chain.node.ExitCode;

public interface Node {

    default ExitCode execute(Context context, KvLogger logger) {
        executeAndContinue(context, logger);
        return null;
    }

    default void executeAndContinue(Context context, KvLogger logger) {
    }
}
