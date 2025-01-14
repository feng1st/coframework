package io.codeone.framework.logging.plugin;

import lombok.Data;

@Data
public class LoggingPluginTestExceptionParam {

    private Object attr;

    public Object getAttr() {
        throw new IllegalStateException();
    }
}
