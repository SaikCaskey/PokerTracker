package com.github.saikcaskey.pokertracker.libs.domain.datasource

import com.github.saikcaskey.pokertracker.libs.domain.models.User
import kotlinx.coroutines.flow.StateFlow

interface UserDataSource {
    val storedUser: StateFlow<User?>
}
