package com.github.saikcaskey.pokertracker.build_logic.convention.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal inline fun <reified T> Project.configureExtension(noinline action: T.() -> Unit) =
    extensions.configure(T::class.java, action)

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().single()
