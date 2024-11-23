package io.codeone.framework.api.checkargs.domain.param;

import io.codeone.framework.api.checkargs.domain.constants.GandalfsQuotes;
import io.codeone.framework.api.parameter.BaseParam;
import io.codeone.framework.api.util.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class Passenger extends BaseParam {

    private static final Set<String> DEMONS;

    static {
        Set<String> demons = new HashSet<>();
        demons.add("balrog");
        DEMONS = demons;
    }

    private String name;

    @Override
    public void validate() {
        Validator.requireNotIn(name.toLowerCase(), DEMONS,
                GandalfsQuotes.YOU_SHALL_NOT_PASS);
    }
}
