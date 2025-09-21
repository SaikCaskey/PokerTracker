package com.github.saikcaskey.pokertracker.domain.models

sealed class SettingsItem {
    abstract val text: String?
    abstract val bottomDivider: Boolean

    data class Header(
        override val text: String? = "Header",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class Subheader(
        override val text: String? = "Subheader",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class Text(
        override val text: String? = "Text",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()

    data class Check(
        override val text: String? = "Check",
        override val bottomDivider: Boolean = false,
    ) : SettingsItem()
}
