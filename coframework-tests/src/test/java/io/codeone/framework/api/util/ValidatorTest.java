package io.codeone.framework.api.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

class ValidatorTest {

    @Test
    void requireTrue() {
        Validator.requireTrue(true, "true");
        Assertions.assertEquals("false",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireTrue(false, "false")).getMessage());
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireTrue(null, "null")).getMessage());
    }

    @Test
    void requireFalse() {
        Validator.requireFalse(false, "false");
        Assertions.assertEquals("true",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireFalse(true, "true")).getMessage());
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireFalse(null, "null")).getMessage());
    }

    @Test
    void requireNonTrue() {
        Validator.requireNonTrue(false, "false");
        Validator.requireNonTrue(null, "null");
        Assertions.assertEquals("true",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonTrue(true, "true")).getMessage());
    }

    @Test
    void requireNonFalse() {
        Validator.requireNonFalse(true, "true");
        Validator.requireNonFalse(null, "null");
        Assertions.assertEquals("false",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonFalse(false, "false")).getMessage());
    }

    @Test
    void requireNonNull() {
        Validator.requireNonNull(new Object(), "non-null");
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonNull(null, "null")).getMessage());
    }

    @Test
    void requireNull() {
        Validator.requireNull(null, "null");
        Assertions.assertEquals("non-null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNull(new Object(), "non-null")).getMessage());
    }

    @Test
    void requireNonEmptyString() {
        Validator.requireNonEmpty("string", "string");
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty((String) null, "null")).getMessage());
        Assertions.assertEquals("empty",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty("", "empty")).getMessage());
    }

    @Test
    void requireNonEmptyCollection() {
        Validator.requireNonEmpty(Collections.singletonList(null), "list");
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty((Collection<?>) null, "null")).getMessage());
        Assertions.assertEquals("empty",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty(Collections.emptyList(), "empty")).getMessage());
    }

    @Test
    void requireNonEmptyMap() {
        Validator.requireNonEmpty(Collections.singletonMap(null, null), "map");
        Assertions.assertEquals("null",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty((Map<?, ?>) null, "null")).getMessage());
        Assertions.assertEquals("empty",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNonEmpty(Collections.emptyMap(), "empty")).getMessage());
    }

    @Test
    void requireEmptyString() {
        Validator.requireEmpty((String) null, "null");
        Validator.requireEmpty("", "empty");
        Assertions.assertEquals("string",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireEmpty("string", "string")).getMessage());
    }

    @Test
    void requireEmptyCollection() {
        Validator.requireEmpty((Collection<?>) null, "null");
        Validator.requireEmpty(Collections.emptyList(), "empty");
        Assertions.assertEquals("list",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireEmpty(Collections.singletonList(null), "list")).getMessage());
    }

    @Test
    void requireEmptyMap() {
        Validator.requireEmpty((Map<?, ?>) null, "null");
        Validator.requireEmpty(Collections.emptyMap(), "empty");
        Assertions.assertEquals("map",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireEmpty(Collections.singletonMap(null, null), "map")).getMessage());
    }

    @Test
    void requireEquals() {
        Validator.requireEquals(null, null, "equals");
        Validator.requireEquals(0, 0, "equals");
        Validator.requireEquals(0L, 0L, "equals");
        Assertions.assertEquals("not-equals",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireEquals(null, 0, "not-equals")).getMessage());
        Assertions.assertEquals("not-equals",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireEquals(0, 0L, "not-equals")).getMessage());
    }

    @Test
    void requireNotEquals() {
        Validator.requireNotEquals(null, 0, "not-equals");
        Validator.requireNotEquals(0, 0L, "not-equals");
        Assertions.assertEquals("equals",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNotEquals(null, null, "equals")).getMessage());
        Assertions.assertEquals("equals",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNotEquals(0, 0, "equals")).getMessage());
        Assertions.assertEquals("equals",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNotEquals(0L, 0L, "equals")).getMessage());
    }

    @Test
    void requireIn() {
        Validator.requireIn(null, Arrays.asList(null, 0), "in");
        Validator.requireIn(0, Arrays.asList(null, 0), "in");
        Assertions.assertEquals("not-in",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireIn(0, null, "not-in")).getMessage());
        Assertions.assertEquals("not-in",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireIn(0, Collections.emptyList(), "not-in")).getMessage());
        Assertions.assertEquals("not-in",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireIn(1, Arrays.asList(null, 0), "not-in")).getMessage());
    }

    @Test
    void requireNotIn() {
        Validator.requireNotIn(0, null, "not-in");
        Validator.requireNotIn(0, Collections.emptyList(), "not-in");
        Validator.requireNotIn(1, Arrays.asList(null, 0), "not-in");
        Assertions.assertEquals("in",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNotIn(null, Arrays.asList(null, 0), "in")).getMessage());
        Assertions.assertEquals("in",
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> Validator.requireNotIn(0, Arrays.asList(null, 0), "in")).getMessage());
    }
}