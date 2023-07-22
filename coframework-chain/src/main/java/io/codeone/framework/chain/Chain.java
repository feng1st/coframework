package io.codeone.framework.chain;

import io.codeone.framework.chain.dag.ChainState;
import io.codeone.framework.chain.dag.Dag;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.logging.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;

@RequiredArgsConstructor(staticName = "of")
@Slf4j
public class Chain<T> {

    private final ChainSpec spec;

    private final List<Node> nodes;

    private Dag<Node> nodeDag;

    public T execute() {
        return execute(Context.of(null));
    }

    public T execute(Context<T> context) {
        logChain(context);
        for (Node node : nodes) {
            if (executeNode(context, node)) {
                break;
            }
        }
        return context.getTarget();
    }

    public T executeAsync(Context<T> context, Executor executor) throws InterruptedException {
        logChain(context);

        ChainState state = ChainState.of(nodeDag);

        while (state.isRunning()) {
            List<Node> nodes = state.pullNodes();
            for (Node node : nodes) {
                executor.execute(() -> state.finishNode(node, executeNode(context, node)));
            }
            state.waitNodes();
        }

        return context.getTarget();
    }

    private void logChain(Context<T> context) {
        Log chainLog = Log.newBuilder()
                .logger(log)
                .scene(spec.getName());
        context.log(chainLog::addArg);
        chainLog.success().log();
    }

    private boolean executeNode(Context<T> context, Node node) {
        Log nodeLog = Log.newBuilder()
                .logger(log)
                .scene(spec.getName())
                .method(node.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        try {
            if (node.execute(context, nodeLog::addArg)) {
                nodeLog.success(true);
                return true;
            }
            nodeLog.success(false);
            return false;
        } catch (Throwable t) {
            nodeLog.error(t);
            throw t;
        } finally {
            nodeLog.elapsed(System.currentTimeMillis() - start).log();
        }
    }
}
