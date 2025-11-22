package com.github.saikcaskey.pokertracker.stats.extensions

import com.github.saikcaskey.pokertracker.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest

fun <T> Flow<User?>.flatMapWithUserId(
    transform: (userId: Long) -> Flow<T>,
): Flow<T> = flatMapLatest { user ->
    if (user == null) {
        emptyFlow()
    } else {
        transform(user.id)
    }
}
