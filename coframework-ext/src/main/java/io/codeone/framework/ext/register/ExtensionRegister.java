package io.codeone.framework.ext.register;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.repo.ExtensionRepo;
import io.codeone.framework.ext.util.ClassUtils;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class ExtensionRegister {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ExtensionRepo extensionRepo;

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansWithAnnotation(Extension.class).forEach(this::register);
    }

    private void register(String beanName, Object ext) {
        Class<?> extClass = ClassUtils.getTargetClass(ext);

        Extension extAnno = extClass.getAnnotation(Extension.class);
        BizScenario bizScenario = BizScenario.of(extAnno.bizId(), extAnno.scenario());

        List<Class<?>> extensibleClasses = ExtUtils.getAllExtensibleClasses(extClass);
        if (extensibleClasses.isEmpty()) {
            throw new IllegalStateException(beanName + " did not extend anything");
        }

        for (Class<?> extensibleClass : extensibleClasses) {
            extensionRepo.putExtension(extensibleClass, bizScenario, ext);
        }
    }
}
