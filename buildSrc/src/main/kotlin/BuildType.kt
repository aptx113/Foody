interface BuildType {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isTestCoverageEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled: Boolean = false
    override val isTestCoverageEnabled: Boolean = true

    const val APPLICATION_ID_SUFFIX = ".debug"
    const val VERSION_NAME_SUFFIX = "-DEBUG"
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled: Boolean = true
    override val isTestCoverageEnabled: Boolean = false
}
