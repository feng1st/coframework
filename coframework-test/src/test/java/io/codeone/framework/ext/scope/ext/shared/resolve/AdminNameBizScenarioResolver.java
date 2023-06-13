package io.codeone.framework.ext.scope.ext.shared.resolve;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.scope.BizScenarioResolver;
import io.codeone.framework.ext.shared.constants.ExtConstants;
import org.springframework.stereotype.Component;

@Component
public class AdminNameBizScenarioResolver implements BizScenarioResolver {

    @Override
    public BizScenario resolve(Object[] args) {
        String adminName = (String) args[0];
        if ("John".equals(adminName)) {
            return BizScenario.ofBizId(ExtConstants.BIZ2);
        } else if ("Bob".equals(adminName)) {
            return BizScenario.ofBizId(ExtConstants.BIZ3);
        }
        return BizScenario.ofBizId(ExtConstants.BIZ1);
    }
}
