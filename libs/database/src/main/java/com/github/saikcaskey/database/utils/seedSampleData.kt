package com.github.saikcaskey.database.utils

import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.extensions.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.domain.extensions.plusMinutes
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import kotlin.random.Random

fun PokerTrackerDatabase.seedSampleData() {
    SampleDataSeederImpl().seedSampleData(this)
}

fun interface SampleDataSeeder {
    fun seedSampleData(database: PokerTrackerDatabase)
}

class SampleDataSeederImpl : SampleDataSeeder {

    override fun seedSampleData(database: PokerTrackerDatabase) {
        database.userQueries.insert("Test User", nowAsLocalDateTime().toString())
        val userId = database.userQueries.lastInsertRowId().executeAsOne()
        repeat(5) {
            val offset = timeOffsets.random()
            val baseDate = nowAsLocalDateTime().date
                .plus(offset)
                .atStartOfDayInstant()
                .asLocalDateTime()

            insertVenueWithEventAndExpenses(
                database = database,
                userId = userId,
                venueName = venueNames.random(),
                description = venueDescriptions.random(),
                eventName = eventNames.random(),
                eventDescription = eventDescriptions.randomDescription(),
                baseDate = baseDate
            )
        }
    }

    private fun insertVenueWithEventAndExpenses(
        database: PokerTrackerDatabase,
        venueName: String,
        description: String,
        eventName: String,
        eventDescription: String,
        baseDate: LocalDateTime,
        userId: Long,
    ) {
        database.venueQueries.insert(
            user_id = userId,
            name = venueName,
            address = "123 Poker Ave",
            description = description,
            created_at = baseDate.toString()
        )
        val venueId = database.venueQueries.lastInsertRowId().executeAsOne()
        val gameType = listOf("CASH", "TOURNAMENT").random()
        database.eventQueries.insert(
            user_id = userId,
            venue_id = venueId,
            name = eventName,
            date = baseDate.toString(),
            game_type = gameType,
            description = eventDescription,
            created_at = baseDate.toString(),
        )
        val eventId = database.eventQueries.lastInsertRowId().executeAsOne()
        insertSimulatedExpenses(database, userId, eventId, venueId, baseDate)
    }

    private fun insertSimulatedExpenses(
        database: PokerTrackerDatabase,
        userId: Long,
        eventId: Long,
        venueId: Long,
        baseDate: LocalDateTime,
    ) {
        val date = baseDate.toString()
        val createdAt = nowAsLocalDateTime().toString()
        val buyInAmount = 200.0
        // Start with a BUY_IN
        database.expenseQueries.insert(
            user_id = userId,
            event_id = eventId,
            venue_id = venueId,
            type = "BUY_IN",
            amount = buyInAmount,
            description = "Buy-in",
            date = date,
            created_at = createdAt
        )
        database.expenseQueries.lastInsertRowId().executeAsOne()

        // Add some random expenses
        val extraCount = Random.nextInt(3, 15)
        repeat(extraCount) {
            val type = extraExpenseTypes.random()
            val amount = type.randomExpenseAmount()
            val note = expenseDescriptions.random()
            database.expenseQueries.insert(
                event_id = eventId,
                user_id = userId,
                venue_id = venueId,
                type = type.toString(),
                amount = amount,
                description = note,
                date = baseDate.plusMinutes(extraCount).toString(),
                created_at = createdAt
            )
            database.expenseQueries.lastInsertRowId().executeAsOne()
        }

        val cashOutAmount = Random.nextDouble(0.00, 5_000.0)
        database.expenseQueries.insert(
            event_id = eventId,
            venue_id = venueId,
            user_id = userId,
            type = "CASH_OUT",
            amount = cashOutAmount,
            description = "Cashout",
            date = date,
            created_at = createdAt
        )
        database.expenseQueries.lastInsertRowId().executeAsOne()
    }
}

private val venueNames = listOf(
    "Bellagio",
    "The Mirage",
    "ARIA",
    "Wynn",
    "Caesars Palace",
    "MGM Grand",
    "Red Rock",
    "Golden Nugget"
)

private val venueDescriptions = listOf(
    "Famous poker room",
    "Luxury experience",
    "Tight games, good drinks",
    "Great cash tables",
    "Mixed games available",
    "Best high roller room",
    "Cheap drinks and loose games"
)

private val eventNames = listOf(
    "Nightly $200 Tournament",
    "Deepstack Saturday",
    "Sunday Shootout",
    "Midweek Madness",
    "High Roller NLHE",
    "Noon Turbo",
    "Rebuy Rumble"
)

private val eventDescriptions = listOf(
    "Great structure, slow blinds",
    "They know my face",
    "Fast-paced action",
    "New player friendly",
    "Lots of drinkers",
    "Lots of experts",
    "Lots of newbies",
    "Lots of re-entries",
    "Lots of regulars",
    "High rake, but soft field",
    "Tight aggressive tables",
)

private val extraExpenseTypes = listOf(
    ExpenseType.FOOD,
    ExpenseType.DRINKS,
    ExpenseType.TRANSPORT,
    ExpenseType.MISC,
    ExpenseType.ADD_ON,
)

private val expenseDescriptions = listOf(
    "Lunch",
    "Taxi fare",
    "Beer",
    "Snacks",
    "Tip to dealer",
    "Parking",
    "Coffee",
    "Late reg fee"
)

private val timeOffsets = listOf(
    DatePeriod(days = -14),
    DatePeriod(days = -7),
    DatePeriod(days = -3),
    DatePeriod(days = -2),
    DatePeriod(days = -1),
    DatePeriod(days = 1),
    DatePeriod(days = 5),
    DatePeriod(days = 7),
    DatePeriod(days = 14),
    DatePeriod(months = -1),
    DatePeriod(months = 1),
    DatePeriod(months = -2),
    DatePeriod(months = 2),
    DatePeriod(months = -3),
    DatePeriod(months = 3),
)

private fun ExpenseType.randomExpenseAmount(): Double {
    return when (this) {
        ExpenseType.ADD_ON -> Random.nextDouble(50.0, 500.0)
        ExpenseType.REBUY -> Random.nextDouble(200.0, 1000.0)
        ExpenseType.DRINKS -> Random.nextDouble(5.0, 1000.0)
        ExpenseType.FINE -> if (Random.nextInt(9) > 7) {
            Random.nextDouble(600.0, 1000.0)
        } else {
            Random.nextDouble(5.0, 20.0)
        }

        else -> Random.nextDouble(10.0, 40.0)
    }
}

private fun List<String>.randomDescription(): String {
    return List(Random.nextInt(5)) { random() }
        .distinct()
        .joinToString(", ")
        .lowercase()
        .replaceFirstChar(Char::uppercaseChar)
}
