package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import org.springframework.stereotype.Component;

@Component
public class ExtensionSessionTestResolver implements BizScenarioResolver {

    @Override
    public BizScenario resolve(Object[] args) {
        if (args.length >= 1
                && args[0] instanceof String) {
            return BizScenario.ofBizId((String) args[0]);
        }
        return null;
    }
}
