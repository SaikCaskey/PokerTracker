package com.github.saikcaskey.database.seed

import com.github.saikcaskey.pokertracker.database.PokerTrackerDatabase
import com.github.saikcaskey.pokertracker.domain.extensions.asLocalDateTime
import com.github.saikcaskey.pokertracker.domain.extensions.atStartOfDayInstant
import com.github.saikcaskey.pokertracker.domain.extensions.plusMinutes
import com.github.saikcaskey.pokertracker.domain.models.ExpenseType
import com.github.saikcaskey.pokertracker.domain.util.nowAsLocalDateTime
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import java.util.UUID
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Instant

class SampleDataSeederImpl(
    private val database: PokerTrackerDatabase,
) : SampleDataSeeder {

    private val fixedVenues = listOf(
        Triple("Bellagio", "High-stakes cash games.", "123 Bellagio Way"),
        Triple("Golden Nugget", "Downtown, loose action.", "711 Fremont St"),
        Triple("Wynn", "Modern and upscale.", "3131 Las Vegas Blvd S")
    )

    private val fixedEvents = listOf(
        Pair("Nightly $200 Tournament", "Slow structure tournament."),
        Pair("1/2 NLHE Cash Game", "Standard cash game."),
        Pair("5/10 NLHE Cash Game", "Mid-stakes cash game.")
    )


    /**
     * Seeds a fixed, predictable set of data: 3 Venues, 3 Events (one per venue), and associated expenses.
     */
    override fun smokeTest(selectedUser: Long?) {
        fixedVenues.forEachIndexed { index, (venueName, description, address) ->
            val (eventName, eventDescription) = fixedEvents[index % fixedEvents.size]
            val baseDate = dateWithRandomOffset()

            runCatching {
                insertVenueWithEventAndExpenses(
                    userId = selectedUser ?: database.userQueries.lastInsertRowId().executeAsOne(),
                    venueName = venueName,
                    address = address,
                    description = description,
                    eventName = eventName,
                    eventDescription = eventDescription,
                    baseDate = baseDate,
                    // Use a slightly less random cash-out for smoketest
                    cashOutAmount = Random.nextDouble(100.0, 500.0)
                )
            }
        }
    }

    /**
     * Seeds one session with a high cash-out (winning day).
     */
    override fun goodDay(selectedUser: Long) {
        val baseDate = dateWithRandomOffset()
        val (venueName, description, address) = fixedVenues.random()
        val (eventName, eventDescription) = fixedEvents.random()

        insertVenueWithEventAndExpenses(
            userId = selectedUser,
            venueName = "$venueName (Good Day)",
            address = address,
            description = description,
            eventName = "$eventName - Big Win",
            eventDescription = "$eventDescription Player got lucky.",
            baseDate = baseDate,
            cashOutAmount = Random.nextDouble(600.0, 1000.0)
        )
    }

    /**
     * Seeds one session with a low cash-out (losing day).
     */
    override fun badDay(selectedUser: Long) {
        val baseDate = dateWithRandomOffset()
        val (venueName, description, address) = fixedVenues.random()
        val (eventName, eventDescription) = fixedEvents.random()

        insertVenueWithEventAndExpenses(
            userId = selectedUser,
            venueName = "$venueName (Bad Day)",
            address = address,
            description = description,
            eventName = "$eventName - Big Loss",
            eventDescription = "$eventDescription Player ran terrible.",
            baseDate = baseDate,
            cashOutAmount = Random.nextDouble(0.00, 50.0)
        )
    }

    /**
     * Inserts a new user with a random name and returns the user ID.
     */
    override fun user(): Long {
        val randomName = "User ${UUID.randomUUID().toString().take(6)}"
        database.userQueries.insert(randomName, nowAsLocalDateTime().toString())
        return database.userQueries.lastInsertRowId().executeAsOne()
    }

    /**
     * Inserts a venue, an event, and then calls the expense simulation.
     */
    private fun insertVenueWithEventAndExpenses(
        userId: Long,
        venueName: String,
        address: String,
        description: String,
        eventName: String,
        eventDescription: String,
        baseDate: Instant,
        cashOutAmount: Double,
    ) {
        database.venueQueries.insert(
            user_id = userId,
            name = venueName,
            address = address,
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
        insertSimulatedExpenses(userId, eventId, venueId, baseDate, cashOutAmount)
    }

    /**
     * Generates and inserts all expenses for an event using the batch helper for sign control.
     */
    private fun insertSimulatedExpenses(
        userId: Long,
        eventId: Long,
        venueId: Long,
        baseDate: Instant,
        cashOutAmount: Double,
    ) {
        val expenseList = mutableListOf<ExpenseSeedData>()
        val buyInAmount = 200.0

        expenseList.add(
            ExpenseSeedData(
                type = ExpenseType.BUY_IN,
                amount = buyInAmount,
                description = "Initial Buy-in",
                date = baseDate
            )
        )

        val extraCount = Random.nextInt(3, 8)
        repeat(extraCount) { index ->
            val type = randomExpenseTypes.random()
            val amount = type.randomExpenseAmount()
            val note = expenseDescriptions.random()
            expenseList.add(
                ExpenseSeedData(
                    type = type,
                    amount = amount,
                    description = note,
                    date = baseDate.asLocalDateTime().plusMinutes(index + 1)
                )
            )
        }

        expenseList.add(
            ExpenseSeedData(
                type = ExpenseType.CASH_OUT,
                amount = cashOutAmount,
                description = "Final Cashout",
                date = baseDate.asLocalDateTime().plusMinutes(extraCount + 2)
            )
        )

        database.insertExpenseBatch(
            userId = userId,
            eventId = eventId,
            venueId = venueId,
            expenses = expenseList
        )
    }
}

private val randomExpenseTypes = listOf(
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

private fun dateWithRandomOffset(): Instant {
    val offset = timeOffsets.random()
    return nowAsLocalDateTime().date
        .plus(offset)
        .atStartOfDayInstant()
}

private fun ExpenseType.randomExpenseAmount(): Double {
    return when (this) {
        ExpenseType.ADD_ON -> Random.nextDouble(50.0, 500.0)
        ExpenseType.REBUY -> Random.nextDouble(200.0, 1000.0)
        ExpenseType.DRINKS -> Random.nextDouble(5.0, 100.0)
        ExpenseType.FINE -> if (Random.nextInt(9) > 7) {
            Random.nextDouble(60.0, 100.0)
        } else {
            Random.nextDouble(5.0, 20.0)
        }

        else -> Random.nextDouble(10.0, 40.0)
    }
}

private data class ExpenseSeedData(
    val type: ExpenseType,
    val amount: Double,
    val description: String,
    val date: Instant,
)

private fun PokerTrackerDatabase.insertExpenseBatch(
    userId: Long,
    eventId: Long?,
    venueId: Long?,
    expenses: List<ExpenseSeedData>,
) {
    val createdAt = nowAsLocalDateTime().toString()

    expenses.forEach { data ->
        expenseQueries.insert(
            user_id = userId,
            event_id = eventId,
            venue_id = venueId,
            type = data.type.toString(),
            amount = data.amount,
            description = data.description,
            date = data.date.toString(),
            created_at = createdAt
        )
    }
}
