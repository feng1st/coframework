package io.codeone.framework.api.extoresult.domain.exception;

import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.exception.BaseException;

public class MyException extends BaseException {

    public MyException(ApiError error, Throwable cause) {
        super(error, cause);
    }
}
