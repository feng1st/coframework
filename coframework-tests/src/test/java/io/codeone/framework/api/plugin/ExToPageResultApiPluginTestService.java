package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.shared.BizException;
import org.springframework.stereotype.Service;

@API
@Service
public class ExToPageResultApiPluginTestService {

    public PageResult<Object> illegalArgumentException() {
        throw new IllegalArgumentException("Illegal argument");
    }

    public PageResult<Object> illegalStateException() {
        throw new IllegalStateException("Illegal state");
    }

    public PageResult<Object> apiExceptionCode() {
        throw new BizException(CommonCodes.SYS_ERROR);
    }

    public PageResult<Object> apiExceptionCodeMessage() {
        throw new BizException(CommonCodes.SYS_ERROR,
                "System error");
    }

    public PageResult<Object> apiExceptionCodeCause() {
        throw new BizException(CommonCodes.SYS_ERROR,
                new IllegalStateException("Illegal state"));
    }

    public PageResult<Object> apiExceptionCodeMessageCause() {
        throw new BizException(CommonCodes.SYS_ERROR,
                "System error",
                new IllegalStateException("Illegal state"));
    }

    @CustomErrorMessage("Custom error message")
    public PageResult<Object> customErrorMessage() {
        throw new IllegalStateException("Illegal state");
    }
}
