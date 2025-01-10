package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.exception.ServerErrors;
import io.codeone.framework.api.response.PageResult;
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

    public PageResult<Object> code() {
        throw new ApiException("SYS_ERROR", false);
    }

    public PageResult<Object> codeMessage() {
        throw new ApiException("SYS_ERROR", false, "System error");
    }

    public PageResult<Object> codeCause() {
        throw new ApiException("SYS_ERROR", false, new IllegalStateException("Illegal state"));
    }

    public PageResult<Object> codeMessageCause() {
        throw new ApiException("SYS_ERROR", false, "System error", new IllegalStateException("Illegal state"));
    }

    public PageResult<Object> apiError() {
        throw new ApiException(ServerErrors.INTERNAL_SYS_ERROR);
    }

    public PageResult<Object> apiErrorMessage() {
        throw new ApiException(ServerErrors.INTERNAL_SYS_ERROR,
                "System error");
    }

    public PageResult<Object> apiErrorCause() {
        throw new ApiException(ServerErrors.INTERNAL_SYS_ERROR,
                new IllegalStateException("Illegal state"));
    }

    public PageResult<Object> apiErrorMessageCause() {
        throw new ApiException(ServerErrors.INTERNAL_SYS_ERROR,
                "System error",
                new IllegalStateException("Illegal state"));
    }

    @CustomErrorMessage("Custom error message")
    public PageResult<Object> customErrorMessage() {
        throw new IllegalStateException("Illegal state");
    }
}
