package com.github.saikcaskey.pokertracker.libs.database.seed

interface SampleDataSeeder {
    fun smokeTest(selectedUser: Long?)
    fun goodDay(selectedUser: Long)
    fun badDay(selectedUser: Long)
    fun user(): Long
}
