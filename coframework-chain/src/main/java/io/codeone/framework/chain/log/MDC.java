package io.codeone.framework.chain.log;

import io.codeone.framework.chain.Chainable;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.common.log.util.LogUtils;
import io.codeone.framework.common.util.ClassUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

@UtilityClass
@Slf4j
public class MDC {

    private final ThreadLocal<Deque<Map<Object, Object>>> stack = ThreadLocal.withInitial(LinkedList::new);

    @SneakyThrows
    public boolean wrap(Invokable<Boolean> invokable) {
        stack.get().push(new LinkedHashMap<>());
        try {
            return invokable.invoke();
        } finally {
            stack.get().pop();
        }
    }

    public void put(Object key, Object value) {
        Map<Object, Object> map = stack.get().peek();
        if (map != null) {
            map.put(key, value);
        }
    }

    public void log(Context context, Chainable chainable, Object resultOrException, long elapsed) {
        Map<Object, Object> params = stack.get().peek();

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
        if (!CollectionUtils.isEmpty(params)) {
            map.put("params", params);
        }

        if (resultOrException instanceof Throwable) {
            log.error("{}", LogUtils.format(map));
        } else {
            log.info("{}", LogUtils.format(map));
        }
    }
}
