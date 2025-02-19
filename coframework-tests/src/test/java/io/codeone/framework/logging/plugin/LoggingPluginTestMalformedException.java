package io.codeone.framework.logging.plugin;

public class LoggingPluginTestMalformedException extends RuntimeException {

    @Override
    public String toString() {
        throw new IllegalStateException();
    }
}
