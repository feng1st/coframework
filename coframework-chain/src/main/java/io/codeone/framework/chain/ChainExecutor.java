package io.codeone.framework.chain;

import io.codeone.framework.api.context.Key;
import io.codeone.framework.chain.chain.Chain;
import io.codeone.framework.chain.factory.ChainFactory;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executor;

@UtilityClass
public class ChainExecutor {

    private ChainFactory chainFactory;

    public void registerChainFactory(ChainFactory chainFactory) {
        ChainExecutor.chainFactory = chainFactory;
    }

    public void execute(ChainSpec chainSpec) {
        execute(chainSpec, null, null, null);
    }

    public <T> T execute(ChainSpec chainSpec,
                         Key resultKey) {
        return execute(chainSpec, null, null, resultKey);
    }

    public void execute(ChainSpec chainSpec,
                        Context context) {
        execute(chainSpec, context, null, null);
    }

    public <T> T execute(ChainSpec chainSpec,
                         Context context,
                         Key resultKey) {
        return execute(chainSpec, context, null, resultKey);
    }

    public void execute(ChainSpec chainSpec,
                        Executor executor) {
        execute(chainSpec, null, executor, null);
    }

    public void execute(ChainSpec chainSpec,
                        Context context,
                        Executor executor) {
        execute(chainSpec, context, executor, null);
    }

    public <T> T execute(ChainSpec chainSpec,
                         Executor executor,
                         Key resultKey) {
        return execute(chainSpec, null, executor, resultKey);
    }

    public <T> T execute(ChainSpec chainSpec,
                         Context context,
                         Executor executor,
                         Key resultKey) {
        Chain chain = chainFactory.getChain(chainSpec);
        if (context == null) {
            context = new Context();
        }
        if (executor == null) {
            chain.execute(context);
        } else {
            try {
                chain.executeAsync(context, executor);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        if (resultKey != null) {
            return context.get(resultKey);
        } else {
            return null;
        }
    }
}
