package io.codeone.framework.common.json.mapper;

/**
 * Defines a contract for converting between Java objects and JSON-formatted strings.
 */
public interface JsonMapper {

    /**
     * Serializes an object to its JSON string representation.
     *
     * @param object the object to serialize (may be null)
     * @return JSON string representation of the object
     */
    String toJsonString(Object object);
}
