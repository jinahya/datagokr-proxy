package com.github.jinahya.epost.openapi.proxy;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludePackages;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("long-running tests")
//@Suite
@IncludeClassNamePatterns(".*Test")
@IncludePackages({
        "com.github.jinahya.epost.openapi.proxy"
})
@IncludeTags({
        _TestConstants.TAG_LONG_RUNNING
})
class LongRunningTestSuite {

}
