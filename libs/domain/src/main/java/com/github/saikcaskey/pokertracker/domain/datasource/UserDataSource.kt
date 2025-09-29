package com.github.saikcaskey.pokertracker.domain.datasource

import com.github.saikcaskey.pokertracker.domain.models.User
import kotlinx.coroutines.flow.StateFlow

interface UserDataSource {
    val selectedUserId: StateFlow<Long?>
    val storedUser: StateFlow<User?>
}
