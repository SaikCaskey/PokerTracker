package com.github.saikcaskey.pokertracker.build_logic.convention.utils

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

internal fun Project.gitCommitHash(): String {
        return try {
            val stdout = ByteArrayOutputStream()
            exec {
                commandLine("git", "rev-parse", "--short", "HEAD")
                standardOutput = stdout
                isIgnoreExitValue = true
            }
            stdout.toString().trim()
        } catch (e: Exception) {
            "unknown"
        }
    }
