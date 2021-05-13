package com.kepler.components

import son.ysy.architecture.error.ErrorReporter

internal class ErrorReporterImpl : ErrorReporter {
    override fun report(ex: Throwable) {
        ex.printStackTrace()
    }
}