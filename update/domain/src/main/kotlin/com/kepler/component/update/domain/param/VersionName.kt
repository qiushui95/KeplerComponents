package com.kepler.component.update.domain.param

@JvmInline
value class VersionName(val value: String) {
    init {
        require(value.isNotBlank())
    }
}