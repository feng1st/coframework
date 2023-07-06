package io.codeone.framework.logging.domain.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MyParam {

    private Long id;

    private Address address;

    @Data
    @Accessors(chain = true)
    public static class Address {

        private String city;
    }
}
