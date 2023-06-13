package io.codeone.framework.ext.scope;

import io.codeone.framework.ext.BizScenario;

public interface BizScenarioResolver {

    BizScenario resolve(Object[] args);
}
