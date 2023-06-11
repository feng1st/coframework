package io.codeone.framework.ext.context;

import io.codeone.framework.ext.BizScenario;

import java.util.LinkedList;

public class BizScenarioContext {

    private static final ThreadLocal<LinkedList<BizScenario>> stack = ThreadLocal.withInitial(LinkedList::new);

    public static BizScenario getBizScenario() {
        return stack.get().peek();
    }

    public static <T> T invoke(BizScenario bizScenario, WrappedMethod<T> method) throws Throwable {
        stack.get().push(bizScenario);
        try {
            return method.invoke();
        } finally {
            stack.get().pop();
        }
    }
}
