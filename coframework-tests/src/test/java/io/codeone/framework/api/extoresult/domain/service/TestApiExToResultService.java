package io.codeone.framework.api.extoresult.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.extoresult.domain.exception.MyException;
import io.codeone.framework.api.extoresult.domain.param.MyBizParam;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiExToResultService {

    public Result<Void> withCheckArgs(MyBizParam param) {
        return Result.success();
    }

    @CustomErrorMessage("What's wrong")
    public Result<Void> withCustomMessage() {
        throw new RuntimeException("BE CONFUSED AND PANIC!");
    }

    public Result<Void> apiError() {
        throw new RuntimeException(new MyException(CommonCodes.INVALID_ARGS, "Invalid arguments", new Exception("not the cause")));
    }

    public Result<Void> invalidParam() {
        throw new RuntimeException(new IllegalArgumentException("Negative", new Exception("ignored")));
    }

    public Result<Void> otherException() {
        throw new RuntimeException(new Exception(new Exception(new Exception("Deeply sorry"))));
    }
}
