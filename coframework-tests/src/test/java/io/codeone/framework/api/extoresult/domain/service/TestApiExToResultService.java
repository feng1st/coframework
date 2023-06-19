package io.codeone.framework.api.extoresult.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.extoresult.domain.exception.MyException;
import io.codeone.framework.api.extoresult.domain.param.MyParam;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.exception.SysException;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiExToResultService {

    public Result<Void> withCheckArgs(MyParam param) {
        return Result.success();
    }

    public Result<Void> throwSysError() {
        throw new SysException(CommonErrors.SYS_ERROR);
    }

    @API(sysErrorMessage = "What's wrong")
    public Result<Void> throwSysErrorCustomMessage() {
        throw new SysException(CommonErrors.SYS_ERROR);
    }

    public Result<Void> throwNonSysError() {
        throw new RuntimeException(new MyException(CommonErrors.INVALID_PARAM, new Exception("not the cause")));
    }

    public Result<Void> throwInvalidParam() {
        throw new RuntimeException(new IllegalArgumentException("Negative", new Exception("ignored")));
    }

    public Result<Void> throwException() {
        throw new RuntimeException(new Exception(new Exception(new Exception("Deeply sorry"))));
    }
}
