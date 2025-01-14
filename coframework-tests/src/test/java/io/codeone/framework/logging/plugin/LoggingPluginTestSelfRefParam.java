package io.codeone.framework.logging.plugin;

import lombok.Data;

@Data
public class LoggingPluginTestSelfRefParam {

    private LoggingPluginTestSelfRefParam self = this;
}
