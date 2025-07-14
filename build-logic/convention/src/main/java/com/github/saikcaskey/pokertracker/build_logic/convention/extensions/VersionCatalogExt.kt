package com.github.saikcaskey.pokertracker.build_logic.convention.extensions

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.getPluginId(key: String): String = findPlugin(key).get().get().pluginId

internal fun VersionCatalog.getVersionInt(key: String): Int =
    findVersion(key).get().toString().toInt()
