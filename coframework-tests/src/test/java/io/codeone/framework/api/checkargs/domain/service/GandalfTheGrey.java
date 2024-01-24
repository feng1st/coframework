package io.codeone.framework.api.checkargs.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.checkargs.domain.param.Passenger;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class GandalfTheGrey {

    public Result<Void> letThrough(Passenger passenger) {
        return Result.success();
    }
}
