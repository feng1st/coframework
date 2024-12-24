package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.annotation.ExtensionPoint;

@ExtensionPoint
public interface BizScenarioIteratorTestNoDefaultEp {

    Object execute(BizScenario bizScenario);
}
