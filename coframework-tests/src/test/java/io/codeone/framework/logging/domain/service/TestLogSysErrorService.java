package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.exception.SysException;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.LoggingPresets;
import io.codeone.framework.logging.domain.exception.MyException;
import io.codeone.framework.logging.domain.exception.MySysException;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Logging
@Service
public class TestLogSysErrorService {

    public Result<Void> sysError() {
        throw new SysException(CommonErrors.SYS_ERROR);
    }

    public Result<Void> customSysError() {
        throw new MySysException(CommonErrors.SYS_ERROR);
    }

    public Result<Void> nonSysError() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }

    @API(sysErrorMessage = "This message will be returned but won't be logged")
    public Result<Void> loggedMessageForSysError() {
        throw new SysException(CommonErrors.SYS_ERROR);
    }

    @Logging(LoggingPresets.ERROR_ONLY)
    public Result<Void> stackTraceForSysError() {
        throw new SysException(CommonErrors.SYS_ERROR);
    }

    @Logging(LoggingPresets.ERROR_ONLY)
    public Result<Void> stackTraceForNonSysError() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }
}
