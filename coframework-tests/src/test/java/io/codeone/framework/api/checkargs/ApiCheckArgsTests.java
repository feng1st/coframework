package io.codeone.framework.api.checkargs;

import io.codeone.framework.api.checkargs.domain.constants.GandalfsQuotes;
import io.codeone.framework.api.checkargs.domain.param.Passenger;
import io.codeone.framework.api.checkargs.domain.service.GandalfTheGrey;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApiCheckArgsTests {

    @Resource
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
        Assertions.assertEquals(result.getErrorCode(), CommonErrors.INVALID_PARAM.getCode());
        Assertions.assertEquals(result.getErrorMessage(), GandalfsQuotes.YOU_SHALL_NOT_PASS);
    }
}
