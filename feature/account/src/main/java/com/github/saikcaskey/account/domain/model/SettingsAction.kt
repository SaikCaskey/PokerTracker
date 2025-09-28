package com.github.saikcaskey.account.domain.model

sealed class SettingsAction {
    data object SetRandomUserId : SettingsAction()
    data object ClearUserId : SettingsAction()
    data object ClearDefaultBuyIn : SettingsAction()
    data object ClearAllData : SettingsAction()
    data object AddDummyData : SettingsAction()
}
