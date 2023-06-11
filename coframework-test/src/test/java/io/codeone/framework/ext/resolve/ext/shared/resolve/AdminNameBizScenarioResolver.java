package io.codeone.framework.ext.resolve.ext.shared.resolve;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.constants.TestExtConstants;
import io.codeone.framework.ext.resolve.BizScenarioResolver;
import org.springframework.stereotype.Component;

@Component
public class AdminNameBizScenarioResolver implements BizScenarioResolver {

    @Override
    public BizScenario resolve(Object[] args) {
        String adminName = (String) args[0];
        if ("John".equals(adminName)) {
            return BizScenario.ofBizId(TestExtConstants.BIZ2);
        } else if ("Bob".equals(adminName)) {
            return BizScenario.ofBizId(TestExtConstants.BIZ3);
        }
        return BizScenario.ofBizId(TestExtConstants.BIZ1);
    }
}
