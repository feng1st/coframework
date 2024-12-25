package io.codeone.framework.logging.plugin;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "self")
public class LoggingPluginTestSelfRefParam {

    private LoggingPluginTestSelfRefParam self = this;
}
