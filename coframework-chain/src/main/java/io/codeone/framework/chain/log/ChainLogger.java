package io.codeone.framework.chain.log;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.common.util.ClassUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

/**
 * Logs execution details for chainable units.
 *
 * <p>This utility class records information such as execution time, context, and
 * results for each chainable unit. It integrates seamlessly with {@link LoggingContext}.
 */
@UtilityClass
@Slf4j(topic = "chain")
public class ChainLogger {

    /**
     * Logs the details of a chainable unit execution.
     *
     * <p>The method records information such as execution time, the chain name,
     * parameters, and any results or exceptions.
     *
     * @param context           the execution context
     * @param chainable         the chainable unit being executed
     * @param resultOrException the result or exception produced by the execution
     * @param elapsed           the time taken for execution in milliseconds
     */
    public void log(Context context, Chainable chainable, Object resultOrException, long elapsed) {
        if (chainable instanceof Quiet) {
            return;
        }

        LogMap<String, Object> logMap = new LogMap<>();
        logMap.put("chain", context.chainName());
        logMap.put("node", ClassUtils.getSimpleName(ClassUtils.getTargetClass(chainable)));
        if (context.getBizScenario() != null) {
            logMap.put("bizId", context.getBizScenario().getBizId());
            logMap.put("scenario", context.getBizScenario().getScenario());
        }
        logMap.put("elapsed", elapsed);
        if (resultOrException instanceof Throwable) {
            logMap.put("exception", resultOrException.toString());
        } else if (Objects.equals(resultOrException, false)) {
            logMap.put("break", true);
        }
        Map<Object, Object> params = LoggingContext.getContextMap();
        if (!CollectionUtils.isEmpty(params)) {
            logMap.put("params", new LogMap<>(params));
        }

        if (resultOrException instanceof Throwable) {
            log.error("{}", LogFormatUtils.format(logMap), resultOrException);
        } else {
            log.info("{}", LogFormatUtils.format(logMap));
        }
    }
}
