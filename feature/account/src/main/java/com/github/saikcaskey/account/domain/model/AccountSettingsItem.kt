package com.github.saikcaskey.account.domain.model

import com.github.saikcaskey.pokertracker.domain.models.UserPreference

sealed class AccountSettingsItem {
    abstract val title: String?
    abstract val bottomDivider: Boolean
    abstract val linkedUserPreference: UserPreference<*>?
    abstract val linkedSettingsAction: SettingsAction?

    data class Header(
        override val title: String? = "Header",
        override val bottomDivider: Boolean = true,
        override val linkedSettingsAction: SettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class InfoText(
        override val title: String? = "Text",
        override val bottomDivider: Boolean = false,
        override val linkedSettingsAction: SettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class Button(
        override val title: String? = "Button",
        override val bottomDivider: Boolean = false,
        override val linkedSettingsAction: SettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedUserPreference: UserPreference<*>? = null
    }

    data class Check(
        val value: Boolean = false,
        val subtitle: String? = null,
        override val title: String? = null,
        override val linkedSettingsAction: SettingsAction? = null,
        override val linkedUserPreference: UserPreference<Boolean>,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class Toggle(
        val value: Boolean = false,
        val subtitle: String? = null,
        override val title: String? = null,
        override val linkedUserPreference: UserPreference<Boolean>,
        override val linkedSettingsAction: SettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class TextInput(
        val value: String? = null,
        val initialContent: String? = "Start typing...",
        override val title: String? = null,
        override val linkedUserPreference: UserPreference<String>,
        override val linkedSettingsAction: SettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class NumberInput(
        val value: Int? = null,
        val maxLength: Int = 20,
        val initialContent: String? = "Start typing...",
        override val title: String? = null,
        override val linkedUserPreference: UserPreference<Int>,
        override val linkedSettingsAction: SettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()
}
