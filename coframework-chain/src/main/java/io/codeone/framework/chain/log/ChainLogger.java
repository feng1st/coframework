package io.codeone.framework.chain.log;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.log.util.LogUtils;
import io.codeone.framework.common.util.ClassUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
@Slf4j(topic = "chain")
public class ChainLogger {

    public void log(Context context, Chainable chainable,
                    Map<Object, Object> mdc, Object resultOrException, long elapsed) {
        if (chainable instanceof Quiet) {
            return;
        }

        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("chain", context.chainName());
        map.put("node", ClassUtils.getTargetClass(chainable).getSimpleName());
        if (context.bizScenario() != null) {
            map.put("bizId", context.bizScenario().getBizId());
            map.put("scenario", context.bizScenario().getScenario());
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
            log.error("{}", LogUtils.format(map));
        } else {
            log.info("{}", LogUtils.format(map));
        }
    }
}
