package io.codeone.framework.chain.log;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.common.util.ClassUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Logs execution details for chainable units.
 *
 * <p>This utility class records information such as execution time, context, and
 * results for each chainable unit. It integrates seamlessly with {@link MDC}.
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
     * @param mdc               the logging context
     * @param resultOrException the result or exception produced by the execution
     * @param elapsed           the time taken for execution in milliseconds
     */
    public void log(Context context, Chainable chainable,
                    Map<Object, Object> mdc, Object resultOrException, long elapsed) {
        if (chainable instanceof Quiet) {
            return;
        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("chain", context.chainName());
        map.put("node", ClassUtils.getSimpleName(ClassUtils.getTargetClass(chainable)));
        if (context.getBizScenario() != null) {
            map.put("bizId", context.getBizScenario().getBizId());
            map.put("scenario", context.getBizScenario().getScenario());
        }
        map.put("elapsed", elapsed);
        if (resultOrException instanceof Throwable) {
            map.put("exception", resultOrException.toString());
        } else if (Objects.equals(resultOrException, false)) {
            map.put("break", true);
        }
        if (!CollectionUtils.isEmpty(mdc)) {
            map.put("params", mdc);
        }

        if (resultOrException instanceof Throwable) {
            log.error("{}", LogFormatUtils.format(map), resultOrException);
        } else {
            log.info("{}", LogFormatUtils.format(map));
        }
    }
}
