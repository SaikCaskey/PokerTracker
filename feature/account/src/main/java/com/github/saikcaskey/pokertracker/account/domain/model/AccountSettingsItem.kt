package com.github.saikcaskey.pokertracker.account.domain.model

import com.github.saikcaskey.pokertracker.domain.models.UserPreference

sealed class AccountSettingsItem {
    abstract val title: String?
    abstract val bottomDivider: Boolean
    abstract val linkedPreference: UserPreference<*>?
    abstract val linkedAction: AccountSettingsAction?

    data class Header(
        override val title: String? = "Header",
        override val bottomDivider: Boolean = true,
        override val linkedAction: AccountSettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedPreference: UserPreference<*>? = null
    }

    data class InfoText(
        override val title: String? = "Text",
        override val bottomDivider: Boolean = false,
        override val linkedAction: AccountSettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedPreference: UserPreference<*>? = null
    }

    data class Button(
        override val title: String? = "Button",
        override val bottomDivider: Boolean = false,
        override val linkedAction: AccountSettingsAction? = null,
    ) : AccountSettingsItem() {
        override val linkedPreference: UserPreference<*>? = null
    }

    data class Check(
        val value: Boolean = false,
        val subtitle: String? = null,
        override val title: String? = null,
        override val linkedAction: AccountSettingsAction? = null,
        override val linkedPreference: UserPreference<Boolean>,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class Toggle(
        val value: Boolean = false,
        val subtitle: String? = null,
        override val title: String? = null,
        override val linkedPreference: UserPreference<Boolean>,
        override val linkedAction: AccountSettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class TextInput(
        val value: String? = null,
        val initialContent: String? = "Start typing...",
        override val title: String? = null,
        override val linkedPreference: UserPreference<String>,
        override val linkedAction: AccountSettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class IntegerInput(
        val value: Int? = null,
        val maxLength: Int = 20,
        val initialContent: String? = "Start typing...",
        override val title: String? = null,
        override val linkedPreference: UserPreference<Int>,
        override val linkedAction: AccountSettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class LongInput(
        val value: Long? = null,
        val initialContent: String? = "Start typing...",
        override val title: String? = null,
        override val linkedPreference: UserPreference<Long>,
        override val linkedAction: AccountSettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()

    data class DropdownInput<T>(
        val value: T? = null,
        val suggestions: List<T>,
        override val title: String? = null,
        override val linkedPreference: UserPreference<Long>,
        override val linkedAction: AccountSettingsAction? = null,
        override val bottomDivider: Boolean = false,
    ) : AccountSettingsItem()
}
