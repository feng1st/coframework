package io.codeone.framework.api.sdk.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.sdk.domain.model.NewCustomApiResult;
import org.springframework.stereotype.Service;

@Service
@API
public class TestCustomApiResultService {

    public NewCustomApiResult<Integer> returnSuccess() {
        NewCustomApiResult<Integer> result = new NewCustomApiResult<>();
        result.setSuccess(true);
        result.setData(1);
        return result;
    }

    public NewCustomApiResult<Integer> returnFailed() {
        NewCustomApiResult<Integer> result = new NewCustomApiResult<>();
        result.setSuccess(false);
        result.setCode("code");
        result.setMsg("msg");
        return result;
    }
}
