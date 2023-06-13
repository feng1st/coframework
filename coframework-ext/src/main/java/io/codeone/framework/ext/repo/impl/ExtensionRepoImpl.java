package io.codeone.framework.ext.repo.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.repo.BizScenarioExtension;
import io.codeone.framework.ext.repo.ExtensionCoordinate;
import io.codeone.framework.ext.repo.ExtensionRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class ExtensionRepoImpl implements ExtensionRepo {

    private final Map<ExtensionCoordinate, Object> map = new HashMap<>();

    private final LoadingCache<ExtensionCoordinate, BizScenarioExtension> cache = Caffeine.newBuilder()
            .expireAfterAccess(24, TimeUnit.HOURS)
            .build(k -> {
                for (BizScenario bizScenario : k.getBizScenario()) {
                    Object ext = map.get(k.ofBizScenario(bizScenario));
                    if (ext != null) {
                        return BizScenarioExtension.of(ext, bizScenario);
                    }
                }
                return BizScenarioExtension.ofEmpty();
            });

    @Override
    public void putExtension(Class<?> extensibleClass, BizScenario bizScenario, Object ext) {
        ExtensionCoordinate coordinate = ExtensionCoordinate.of(extensibleClass, bizScenario);
        Object former = map.put(coordinate, ext);
        if (former != null) {
            throw new IllegalStateException("Found duplicate Extensions for '" + coordinate + "'");
        }
    }

    @Override
    public BizScenarioExtension getExtension(Class<?> extensibleClass, BizScenario bizScenario) {
        if (bizScenario == null) {
            throw new IllegalArgumentException("bizScenario is null");
        }

        ExtensionCoordinate coordinate = ExtensionCoordinate.of(extensibleClass, bizScenario);
        BizScenarioExtension bizExt = cache.get(coordinate);
        if (bizExt.isEmpty()) {
            throw new IllegalArgumentException("Could not find Extension for '" + coordinate + "'");
        }
        return bizExt;
    }
}
