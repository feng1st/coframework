package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;

public class BizScenarioResolverCacheTestInvalidResolver implements BizScenarioResolver {

    @Override
    public BizScenario resolve(Object[] args) {
        return null;
    }
}
