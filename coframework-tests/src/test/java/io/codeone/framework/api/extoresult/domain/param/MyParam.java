package io.codeone.framework.api.extoresult.domain.param;

import io.codeone.framework.request.BaseRequest;
import io.codeone.framework.util.ArgChecker;

public class MyParam extends BaseRequest {

    @Override
    public void checkArgs() {
        ArgChecker.check(false, "None accepted");
    }
}
