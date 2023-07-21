package io.codeone.framework.chain.domain.filter;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.TargetFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestChainNon5NFilter extends TargetFilter<Long> {

    @Override
    protected List<Long> filter(List<Long> target, Context<?> context, Logger logger) {
        target = target.stream()
                .filter(o -> o % 5 != 0)
                .collect(Collectors.toList());
        logger.logTarget(target);
        return target;
    }
}
