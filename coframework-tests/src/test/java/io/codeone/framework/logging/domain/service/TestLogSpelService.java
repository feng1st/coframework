package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.domain.param.MyParam;
import org.springframework.stereotype.Service;

@Service
public class TestLogSpelService {

    @Logging(argKvs = {"city", "#a0?.address?.city",
            "id", "#a0?.id",
            "ret", "#r?.data?.id"})
    public Result<MyParam> keyPairs(MyParam param) {
        return Result.success(param);
    }

    @Logging(expSuccess = "#r?.id==1",
            expCode = "#r?.id",
            expMessage = "#r?.id")
    public MyParam code(MyParam param) {
        return param;
    }
}
