package io.codeone.framework.api.shared;

import io.codeone.framework.api.exception.BaseException;

public class BizException extends BaseException {

    public BizException(String code) {
        super(code);
    }

    public BizException(String code, String message) {
        super(code, message);
    }

    public BizException(String code, Throwable cause) {
        super(code, cause);
    }

    public BizException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
