package io.codeone.framework.ext.register;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Extension;
import io.codeone.framework.ext.model.ExtensionCoordinate;
import io.codeone.framework.ext.repo.ExtensionRepo;
import io.codeone.framework.ext.util.ClassUtils;
import io.codeone.framework.ext.util.ExtUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scans and registers all Extension implementation mappings. The mapping key is
 * {@link ExtensionCoordinate} and the value is an Extension instance.
 */
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

        List<Class<?>> extensibleClasses = ExtUtils.getAllExtensibleClasses(extClass);
        if (extensibleClasses.isEmpty()) {
            throw new IllegalStateException(beanName + " did not extend anything");
        }

        Extension extAnno = extClass.getAnnotation(Extension.class);
        List<BizScenario> bizScenarios = new ArrayList<>();
        if (extAnno.scenarios().length > 0) {
            Arrays.stream(extAnno.scenarios())
                    .forEach(o -> bizScenarios.add(BizScenario.of(extAnno.bizId(), o)));
        } else {
            bizScenarios.add(BizScenario.of(extAnno.bizId(), extAnno.scenario()));
        }

        for (Class<?> extensibleClass : extensibleClasses) {
            for (BizScenario bizScenario : bizScenarios) {
                ExtensionCoordinate coordinate = ExtensionCoordinate.of(extensibleClass, bizScenario);
                extensionRepo.putExtension(coordinate, ext);
            }
        }
    }
}
