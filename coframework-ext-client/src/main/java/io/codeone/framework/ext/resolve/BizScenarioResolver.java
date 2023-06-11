package io.codeone.framework.ext.resolve;

import io.codeone.framework.ext.BizScenario;

public interface BizScenarioResolver {

    BizScenario resolve(Object[] args);
}
