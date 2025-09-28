package com.github.saikcaskey.pokertracker.domain.models

sealed class SettingsAction {
    data object SetRandomUserId : SettingsAction()
    data object ClearUserId : SettingsAction()
    data object ClearDefaultBuyIn : SettingsAction()
    data object ClearAllData : SettingsAction()
    data object AddDummyData : SettingsAction()
}
