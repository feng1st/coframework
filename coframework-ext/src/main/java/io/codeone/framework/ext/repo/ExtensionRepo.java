package io.codeone.framework.ext.repo;

import io.codeone.framework.ext.model.BizScenarioExtension;
import io.codeone.framework.ext.model.ExtensionCoordinate;

public interface ExtensionRepo {

    void putExtension(ExtensionCoordinate coordinate, Object ext);

    BizScenarioExtension getExtension(ExtensionCoordinate coordinate);
}
