package io.codeone.framework.logging.domain.param;

import io.codeone.framework.request.BaseRequest;
import io.codeone.framework.util.ArgChecker;

public class Passenger extends BaseRequest {

    private static final String BALROG_NAME = "Balrog";

    private String name;

    public Passenger setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public void checkArgs() {
        ArgChecker.check(!BALROG_NAME.equalsIgnoreCase(name), "YOU SHALL NOT PASS!");
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
