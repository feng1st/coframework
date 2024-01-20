package io.codeone.framework.chain.node;

public interface ExitCode {

    static ExitCode of(String code) {
        return new ExitCode() {
            @Override
            public String toString() {
                return code;
            }
        };
    }
}
