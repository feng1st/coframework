package io.codeone.framework.logging.domain;

import io.codeone.framework.api.API;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.LoggingPresets;
import io.codeone.framework.logging.domain.exception.MyException;
import io.codeone.framework.logging.domain.param.Passenger;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Logging
@Service
public class TestLoggingService {

    @Logging(LoggingPresets.ALL)
    public Result<Void> letThrough(Passenger passenger) {
        return Result.success();
    }

    public Result<Void> throwApiError() {
        throw new RuntimeException(new MyException(CommonErrors.OUTER_SYS_ERROR, new Exception("dropped")));
    }

    public Result<Void> throwInvalidParam() {
        throw new RuntimeException(new IllegalArgumentException("Nah", new Exception("invisible")));
    }

    public Result<Void> throwException() {
        throw new RuntimeException(new Exception(new Exception(new Exception("I'm deeply sorry"))));
    }
}
