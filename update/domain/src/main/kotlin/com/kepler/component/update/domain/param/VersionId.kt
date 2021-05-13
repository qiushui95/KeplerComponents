package com.kepler.component.update.domain.param

@JvmInline
value class VersionId(val value: String) {
    init {
        require(value.isNotBlank())
    }
}