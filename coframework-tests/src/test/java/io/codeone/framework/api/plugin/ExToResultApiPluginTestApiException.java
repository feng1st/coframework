package io.codeone.framework.api.plugin;

import io.codeone.framework.api.exception.BaseException;

public class ExToResultApiPluginTestApiException extends BaseException {

    public ExToResultApiPluginTestApiException(String code) {
        super(code);
    }

    public ExToResultApiPluginTestApiException(String code, String message) {
        super(code, message);
    }

    public ExToResultApiPluginTestApiException(String code, Throwable cause) {
        super(code, cause);
    }

    public ExToResultApiPluginTestApiException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
