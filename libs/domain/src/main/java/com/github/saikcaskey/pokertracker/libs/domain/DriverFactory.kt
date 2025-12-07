package com.github.saikcaskey.pokertracker.libs.domain

import app.cash.sqldelight.db.SqlDriver

interface DriverFactory {
    fun createDriver(): SqlDriver
}
