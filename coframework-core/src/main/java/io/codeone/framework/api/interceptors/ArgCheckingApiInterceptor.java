package io.codeone.framework.api.interceptors;

import io.codeone.framework.api.ApiInterceptor;
import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.Signature;
import io.codeone.framework.request.ApiParam;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.ARG_VALIDATING)
public class ArgCheckingApiInterceptor implements ApiInterceptor<Void> {

    @Override
    public void before(Signature signature, Object[] args) throws Throwable {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
