package io.codeone.framework.logging.plugin;

import lombok.Data;

import java.util.HashMap;
import java.util.Set;

@Data
public class LoggingPluginTestExceptionMapParam extends HashMap<Object, Object> {

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        throw new IllegalStateException();
    }

    @Override
    public int hashCode() {
        throw new IllegalStateException();
    }
}
