package io.codeone.framework.ext.repo.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.repo.ExtensionRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class ExtensionRepoImpl implements ExtensionRepo {

    private final Map<Coordinate, Object> map = new HashMap<>();

    private final LoadingCache<Coordinate, Optional<?>> cache = Caffeine.newBuilder()
            .expireAfterAccess(24, TimeUnit.HOURS)
            .build(k -> {
                for (BizScenario bizScenario : k.getBizScenario()) {
                    Object ext = map.get(k.ofBizScenario(bizScenario));
                    if (ext != null) {
                        return Optional.of(ext);
                    }
                }
                return Optional.empty();
            });

    @Override
    public void putExtension(Class<?> extensibleClass, BizScenario bizScenario, Object ext) {
        Coordinate coordinate = Coordinate.of(extensibleClass, bizScenario);
        Object former = map.put(coordinate, ext);
        if (former != null) {
            throw new IllegalStateException("Found duplicate Extensions for '" + coordinate + "'");
        }
    }

    @Override
    public Object getExtension(Class<?> extensibleClass, BizScenario bizScenario) {
        if (bizScenario == null) {
            throw new IllegalArgumentException("bizScenario is null");
        }

        Coordinate coordinate = Coordinate.of(extensibleClass, bizScenario);
        return cache.get(coordinate)
                .orElseThrow(() -> new IllegalArgumentException("Could not find Extension for '" + coordinate + "'"));
    }

    private static class Coordinate {

        private final Class<?> extensibleClass;

        private final BizScenario bizScenario;

        public static Coordinate of(Class<?> extensibleClass, BizScenario bizScenario) {
            return new Coordinate(extensibleClass, bizScenario);
        }

        public Coordinate ofBizScenario(BizScenario bizScenario) {
            return new Coordinate(this.extensibleClass, bizScenario);
        }

        public Coordinate(Class<?> extensibleClass, BizScenario bizScenario) {
            this.extensibleClass = extensibleClass;
            this.bizScenario = bizScenario;
        }

        public BizScenario getBizScenario() {
            return bizScenario;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return Objects.equals(extensibleClass, that.extensibleClass) && Objects.equals(bizScenario, that.bizScenario);
        }

        @Override
        public int hashCode() {
            return Objects.hash(extensibleClass, bizScenario);
        }

        @Override
        public String toString() {
            return extensibleClass.getSimpleName() + '@' + bizScenario;
        }
    }
}
