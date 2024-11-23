package io.codeone.framework.api.extoresult.domain.exception;

import io.codeone.framework.api.exception.BaseException;

public class MyException extends BaseException {

    public MyException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
