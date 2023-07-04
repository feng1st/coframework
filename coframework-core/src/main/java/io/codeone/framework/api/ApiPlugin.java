package io.codeone.framework.api;

import io.codeone.framework.plugin.Plugin;

/**
 * A special plugin that will be applied automatically on any API service
 * (any class or method annotated by '@API').
 *
 * @see API
 */
public interface ApiPlugin extends Plugin {

    String GROUP = "API";
}
