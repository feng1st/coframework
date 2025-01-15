package io.codeone.framework.logging.plugin;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;

@Data
public class LoggingPluginTestExceptionCollectionParam extends ArrayList<Object> {

    @Override
    public Iterator<Object> iterator() {
        throw new IllegalStateException();
    }

    @Override
    public int hashCode() {
        throw new IllegalStateException();
    }
}
