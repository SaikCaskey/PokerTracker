package com.github.saikcaskey.account.domain.model

sealed class AccountSettingsAction {
    data object SetRandomUserId : AccountSettingsAction()
    data object ClearUserId : AccountSettingsAction()
    data object ClearDefaultBuyIn : AccountSettingsAction()
    data object ClearAllData : AccountSettingsAction()
    sealed class SeedData : AccountSettingsAction() {
        data object SmokeTest : SeedData()
        data object GoodDay : SeedData()
        data object BadDay : SeedData()
        data object User : SeedData()
    }
}
