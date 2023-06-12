package io.codeone.framework.ext.context.resolve;

import io.codeone.framework.ext.repo.BizScenarioResolveRepo;
import io.codeone.framework.ext.resolve.BizScenarioResolve;
import io.codeone.framework.ext.resolve.BizScenarioResolvePolicy;
import io.codeone.framework.ext.resolve.BizScenarioResolver;
import io.codeone.framework.ext.resolve.ResolveFrom;
import io.codeone.framework.ext.util.ClassUtils;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component(BizScenarioResolveScanner.BEAN_NAME)
public class BizScenarioResolveScanner implements BeanPostProcessor {

    public static final String BEAN_NAME = "bizScenarioResolveScanner";

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private BizScenarioResolveRepo bizScenarioResolveRepo;

    @PostConstruct
    private void postConstruct() {
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            if (BEAN_NAME.equals(beanName)) {
                continue;
            }

            Object bean = applicationContext.getBean(beanName);
            Class<?> clazz = ClassUtils.getTargetClass(bean);

            for (Method method : clazz.getMethods()) {
                if (method.getDeclaringClass() == Object.class) {
                    continue;
                }

                BizScenarioResolve resolve = method.getAnnotation(BizScenarioResolve.class);
                if (resolve == null) {
                    resolve = method.getDeclaringClass().getAnnotation(BizScenarioResolve.class);
                    if (resolve == null) {
                        continue;
                    }
                }

                if (resolve.value() == BizScenarioResolvePolicy.CUSTOM) {
                    bizScenarioResolveRepo.putResolver(resolve.customResolver(), getResolver(resolve));
                } else {
                    bizScenarioResolveRepo.putParamIndex(method, getParamIndex(method, resolve));
                }
            }
        }
    }

    private int getParamIndex(Method method, BizScenarioResolve resolve) {
        if (resolve.value() == BizScenarioResolvePolicy.FIRST) {
            for (int i = 0; i < method.getParameters().length; i++) {
                if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                    return i;
                }
            }
        } else if (resolve.value() == BizScenarioResolvePolicy.LAST) {
            for (int i = method.getParameters().length - 1; i >= 0; i--) {
                if (ExtUtils.isBizScenarioParam(method.getParameters()[i].getType())) {
                    return i;
                }
            }
        } else if (resolve.value() == BizScenarioResolvePolicy.SPECIFIED) {
            Integer index = null;
            for (int i = 0; i < method.getParameters().length; i++) {
                Parameter param = method.getParameters()[i];
                if (param.isAnnotationPresent(ResolveFrom.class)) {
                    if (index != null) {
                        throw new IllegalStateException("Found duplicate @ResolveFroms on '" + method + "'");
                    }
                    if (!ExtUtils.isBizScenarioParam(param.getType())) {
                        throw new IllegalStateException("The parameter of '" + method
                                + "' annotated by @ResolveFroms is not a BizScenarioParam");
                    }
                    index = i;
                }
            }
            if (index != null) {
                return index;
            }
        }

        throw new IllegalStateException("Could not find BizScenarioParam on '" + method + "'");
    }

    private BizScenarioResolver getResolver(BizScenarioResolve resolve) {
        return applicationContext.getBean(resolve.customResolver());
    }
}
