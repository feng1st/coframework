package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

public interface BizScenarioResolver {

    BizScenario resolve(Object[] args);
}
