package io.codeone.framework.api.checkargs;

import io.codeone.framework.api.checkargs.domain.constants.GandalfsQuotes;
import io.codeone.framework.api.checkargs.domain.param.Passenger;
import io.codeone.framework.api.checkargs.domain.service.GandalfTheGrey;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiCheckArgsTests {

    @Autowired
    private GandalfTheGrey gandalfTheGrey;

    @Test
    void testFrodo() {
        Result<Void> result = gandalfTheGrey.letThrough(new Passenger().setName("Frodo"));
        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void testBalrog() {
        Result<Void> result = gandalfTheGrey.letThrough(new Passenger().setName("Balrog"));
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(CommonCodes.INVALID_ARGS, result.getErrorCode());
        Assertions.assertEquals(GandalfsQuotes.YOU_SHALL_NOT_PASS, result.getErrorMessage());
    }
}
