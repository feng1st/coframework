package io.codeone.framework.logging.domain.exception;

import io.codeone.framework.exception.ApiError;
import io.codeone.framework.exception.BaseException;
import io.codeone.framework.exception.SysError;

public class MySysException extends BaseException implements SysError {

    public MySysException(ApiError error) {
        super(error);
    }
}
