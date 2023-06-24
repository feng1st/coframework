package io.codeone.framework.api.logging.domain.param;

import io.codeone.framework.request.BaseRequest;
import io.codeone.framework.util.ArgChecker;

public class MyParam extends BaseRequest {

    private Long id;

    public Long getId() {
        return id;
    }

    public MyParam setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public void checkArgs() {
        ArgChecker.checkNotNull(id, "id is null");
    }
}
