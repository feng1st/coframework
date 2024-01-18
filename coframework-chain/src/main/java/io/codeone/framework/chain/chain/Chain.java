package io.codeone.framework.chain.chain;

import io.codeone.framework.chain.Context;
import io.codeone.framework.chain.Node;
import io.codeone.framework.chain.graph.Graph;
import io.codeone.framework.chain.node.ExitCode;
import io.codeone.framework.chain.node.ExitCodes;
import io.codeone.framework.chain.state.AsyncChainState;
import io.codeone.framework.chain.state.ChainState;
import io.codeone.framework.chain.state.SyncChainState;
import io.codeone.framework.logging.Log;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;

@RequiredArgsConstructor(staticName = "of")
@Slf4j
public final class Chain {

    private final Graph<Node> graph;

    @SneakyThrows(InterruptedException.class)
    public void execute(Context context) {
        enterChain(context);

        ChainState state = SyncChainState.of(graph);

        loop:
        while (state.isRunning()) {
            List<Node> nodes = state.pullNodes();
            for (Node node : nodes) {
                if (executeNode(context, node) != null) {
                    break loop;
                }
                state.finishNode(node, null);
            }
        }
    }

    public void executeAsync(Context context, Executor executor) throws InterruptedException {
        enterChain(context);

        ChainState state = AsyncChainState.of(graph);

        while (state.isRunning()) {
            List<Node> nodes = state.pullNodes();
            for (Node node : nodes) {
                executor.execute(() -> {
                    try {
                        state.finishNode(node, executeNode(context, node));
                    } catch (Throwable t) {
                        state.finishNode(node, ExitCodes.EXCEPTION);
                        throw t;
                    }
                });
            }
            state.waitNodes();
        }
    }

    private void enterChain(Context context) {
        Log logger = Log.newBuilder()
                .logger(log)
                .scene(context.getName());
        context.onEnterChain(logger::addArg);
        logger.log();
    }

    private ExitCode executeNode(Context context, Node node) {
        Log logger = Log.newBuilder()
                .logger(log)
                .scene(context.getName())
                .method(node.getClass().getSimpleName());
        long start = System.currentTimeMillis();
        try {
            context.onEnterNode(logger::addArg);
            ExitCode exitCode = node.execute(context, logger::addArg);
            logger.resultBody(exitCode);
            return exitCode;
        } catch (Throwable t) {
            logger.error(t);
            throw t;
        } finally {
            logger.elapsed(System.currentTimeMillis() - start).log();
        }
    }
}
