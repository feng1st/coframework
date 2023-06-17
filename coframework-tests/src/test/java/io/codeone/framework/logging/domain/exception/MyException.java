package io.codeone.framework.logging.domain.exception;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.BaseException;

public class MyException extends BaseException {

    public MyException(ApiError error, Throwable cause) {
        super(error, cause);
    }
}
