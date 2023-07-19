package io.codeone.framework.chain;

import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.logging.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Slf4j
public class Chain<T> {

    private final ChainSpec spec;

    private final List<Node> nodes;

    public T execute(Context<T> context) {
        if (context == null) {
            context = Context.of(null);
        }

        Log chainLog = Log.newBuilder()
                .logger(log)
                .scene(spec.getName());
        context.log(chainLog::addArg);
        chainLog.success().log();

        for (Node node : nodes) {
            Log nodeLog = Log.newBuilder()
                    .logger(log)
                    .scene(spec.getName())
                    .method(node.getClass().getSimpleName());
            long start = System.currentTimeMillis();
            try {
                if (node.execute(context, nodeLog::addArg)) {
                    nodeLog.success(true);
                    break;
                }
                nodeLog.success(false);
            } catch (Throwable t) {
                nodeLog.error(t);
                throw t;
            } finally {
                nodeLog.elapsed(System.currentTimeMillis() - start).log();
            }
        }
        return context.getTarget();
    }

    public T execute() {
        return execute(null);
    }
}
