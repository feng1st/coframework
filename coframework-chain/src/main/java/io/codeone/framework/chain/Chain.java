package io.codeone.framework.chain;

import java.util.Map;

public interface Chain {

    Object execute(Map<String, Object> paramMap);
}
