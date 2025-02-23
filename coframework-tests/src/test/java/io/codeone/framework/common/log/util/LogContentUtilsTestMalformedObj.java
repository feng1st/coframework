package io.codeone.framework.common.log.util;

public class LogContentUtilsTestMalformedObj {

    @Override
    public String toString() {
        throw new IllegalStateException();
    }
}
