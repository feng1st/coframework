package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import org.springframework.stereotype.Component;

@Component
public class ExtensionSessionTestExceptionResolver implements BizScenarioResolver {

    @Override
    public BizScenario resolve(Object[] args) {
        throw new IllegalStateException();
    }
}
