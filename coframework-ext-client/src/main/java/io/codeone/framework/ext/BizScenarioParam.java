package io.codeone.framework.ext;

/**
 * The interface of any parameter which has a BizScenario. The framework uses
 * this type to look up an Extension (one particular implementation) of an
 * Extensible (an interface which can be implemented differently for different
 * BizScenarios).
 */
public interface BizScenarioParam {

    BizScenario getBizScenario();
}
