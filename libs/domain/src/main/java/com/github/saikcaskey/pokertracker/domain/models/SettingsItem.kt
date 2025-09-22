package com.github.saikcaskey.pokertracker.domain.models

sealed class SettingsItem {
    abstract val title: String?
    abstract val bottomDivider: Boolean
    abstract val linkedUserPreference: UserPreference<*>?

    data class Header(
        override val title: String? = "Header",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class Subheader(
        override val title: String? = "Subheader",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class Text(
        override val title: String? = "Text",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class Check(
        val value: Boolean = false,
        val subtitle: String? = "Check",
        override val title: String? = "Check",
        override val linkedUserPreference: UserPreference<Boolean>,
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class Toggle(
        val value: Boolean = false,
        val subtitle: String? = "Toggle",
        override val title: String? = "Toggle",
        override val linkedUserPreference: UserPreference<Boolean>,
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class TextInput(
        val value: String? = null,
        val initialContent: String? = "Start typing...",
        override val title: String? = "Input",
        override val linkedUserPreference: UserPreference<String>,
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class NumberInput(
        val value: Int? = null,
        val maxLength: Int = 20,
        val initialContent: String? = "Start typing...",
        override val title: String? = "Input",
        override val linkedUserPreference: UserPreference<Int>,
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()
}
