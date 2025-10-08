package com.github.saikcaskey.account.domain.model

sealed class AccountSettingsAction {
    data object SetRandomUserId : AccountSettingsAction()
    data object ClearUserId : AccountSettingsAction()
    data object ClearDefaultBuyIn : AccountSettingsAction()
    data object ClearAllData : AccountSettingsAction()
    data object AddDummyData : AccountSettingsAction()
}
