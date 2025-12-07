package com.github.saikcaskey.pokertracker.app.generators

import co.touchlab.kermit.Logger
import com.github.saikcaskey.pokertracker.libs.domain.generators.GeneratorUsernames

class GeneratorUsernamesImpl : GeneratorUsernames {
    private val adjectives = listOf(
        "admiring", "brave", "clever", "dazzling", "eager",
        "fluffy", "gloomy", "happy", "jolly", "kind",
        "lazy", "mighty", "nervous", "peaceful", "quick"
    )
    private val nouns = listOf(
        "albatross", "badger", "chipmunk", "dolphin", "eagle",
        "fox", "gazelle", "horse", "jaguar", "koala",
        "lion", "moose", "newt", "owl", "panda"
    )

    override fun generate(): String {
        val username = "${adjectives.random()}-${nouns.random()}"
        Logger.i("asd generated username: $username")
        return username
    }
}
