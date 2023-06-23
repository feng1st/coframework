package io.codeone.framework.api.interceptors;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiInterceptor;
import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.MethodWrap;
import io.codeone.framework.request.ApiParam;
import org.springframework.stereotype.Component;

/**
 * ArgCheckingApiInterceptor will execute the checkArgs() method of 'ApiParam'
 * type args, upon the invocation of a method of a service, which is annotated
 * by '@API'.
 *
 * @see API
 * @see ApiParam
 */
@Component
@Intercept(Stage.ARG_VALIDATING)
public class ArgCheckingApiInterceptor implements ApiInterceptor<Void> {

    @Override
    public void before(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
