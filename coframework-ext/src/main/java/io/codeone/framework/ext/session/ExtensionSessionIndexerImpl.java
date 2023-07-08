package io.codeone.framework.ext.session;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import io.codeone.framework.ext.repo.ExtensionSessionRepo;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
public class ExtensionSessionIndexerImpl implements ExtensionSessionIndexer {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ExtensionSessionRepo extensionSessionRepo;

    @Override
    public void index(Method method, ExtensionSession annotation) {
        extensionSessionRepo.computeParamIndexIfAbsent(method,
                k -> getParamIndex(method, annotation));
    }

    @Override
    public BizScenario resolve(Method method, Object[] args, ExtensionSession session) {
        int index = extensionSessionRepo.getParamIndex(method);
        if (index != -1) {
            return ((BizScenarioParam) args[index]).getBizScenario();
        }

        BizScenarioResolver resolver = extensionSessionRepo.getResolver(session.customResolver());
        return resolver.resolve(args);
    }

    private int getParamIndex(Method method, ExtensionSession session) {
        if (session.value() == BizScenarioResolvePolicy.FIRST) {
            return tryFirst(method);
        }
        if (session.value() == BizScenarioResolvePolicy.LAST) {
            return tryLast(method);
        }
        if (session.value() == BizScenarioResolvePolicy.SPECIFIED) {
            return trySpecified(method);
        }
        if (session.value() == BizScenarioResolvePolicy.CUSTOM) {
            return tryCustomResolver(method, session);
        }
        // BizScenarioResolvePolicy.AUTO
        try {
            return tryCustomResolver(method, session);
        } catch (Exception ignored) {
        }
        try {
            return trySpecified(method);
        } catch (Exception ignored) {
        }
        return tryFirst(method);
    }

    private int tryFirst(Method method) {
        for (int i = 0; i < method.getParameters().length; i++) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
    }

    private int tryLast(Method method) {
        for (int i = method.getParameters().length - 1; i >= 0; i--) {
            if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                return i;
            }
        }
        throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
    }

    private int trySpecified(Method method) {
        Integer index = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.isAnnotationPresent(ResolveFrom.class)) {
                if (index != null) {
                    throw new IllegalStateException("Found duplicate @ResolveFroms on '" + method + "'");
                }
                if (!ExtUtils.isBizScenarioParam(param.getType())) {
                    throw new IllegalStateException(
                            "The parameter of '" + method + "' annotated by @ResolveFrom is not a BizScenarioParam");
                }
                index = i;
            }
        }
        if (index == null) {
            throw new IllegalStateException(
                    "Could not find BizScenarioParam annotated by @ResolveFrom on '" + method + "'");
        }
        return index;
    }

    private int tryCustomResolver(Method method, ExtensionSession session) {
        try {
            if (session.customResolver() == BizScenarioResolver.class) {
                throw new IllegalStateException("Did not specify BizScenarioResolver for '" + method + "'");
            }
            BizScenarioResolver ignored = applicationContext.getBean(session.customResolver());
        } catch (Exception e) {
            throw new IllegalStateException("Could not find consistent BizScenarioResolver on '" + method + "'");
        }
        return -1;
    }
}
