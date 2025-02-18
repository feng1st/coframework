package io.codeone.framework.common.log.formatter;

/**
 * Interface for formatting log content.
 *
 * <p>Implementations of this interface should define how to format log content
 * before it is outputted.
 */
public interface LogFormatter {

    /**
     * Formats the given content for logging.
     *
     * @param content the content to format
     * @return the formatted log content
     */
    String format(Object content);
}
