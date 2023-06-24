package io.codeone.framework.logging.domain.service;

import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.domain.param.MyParam;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@Service
public class TestLogSpelService {

    @Logging(keyPairs = {"city", "#arg0?.address?.city",
            "id", "#arg0?.id",
            "ret", "#ret?.data?.id"})
    public Result<MyParam> keyPairs(MyParam param) {
        return Result.success(param);
    }

    @Logging(expSuccess = "#ret?.id==1",
            expCode = "#ret?.id",
            expMessage = "#ret?.id")
    public MyParam code(MyParam param) {
        return param;
    }
}
