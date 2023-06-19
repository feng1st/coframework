package io.codeone.framework.api.checkargs.domain.param;

import io.codeone.framework.api.checkargs.domain.constants.GandalfsQuotes;
import io.codeone.framework.request.BaseRequest;
import io.codeone.framework.util.ArgChecker;

import java.util.HashSet;
import java.util.Set;

public class Passenger extends BaseRequest {

    private static final Set<String> DEMONS;

    static {
        Set<String> demons = new HashSet<>();
        demons.add("balrog");
        DEMONS = demons;
    }

    private String name;

    public Passenger setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public void checkArgs() {
        ArgChecker.checkNotIn(name.toLowerCase(), DEMONS,
                GandalfsQuotes.YOU_SHALL_NOT_PASS);
    }
}
