package com.gaiana.game.model

import android.content.Context

/** What survives between launches: how often you died and which endings you've seen. */
class Progress(context: Context) {
    private val prefs = context.getSharedPreferences("progress", Context.MODE_PRIVATE)

    val deaths: Int get() = prefs.getInt(KEY_DEATHS, 0)

    val endings: Set<Ending>
        get() = prefs.getStringSet(KEY_ENDINGS, emptySet()).orEmpty()
            .mapNotNull { name -> Ending.entries.firstOrNull { it.name == name } }
            .toSet()

    fun addDeath() = prefs.edit().putInt(KEY_DEATHS, deaths + 1).apply()

    fun reach(ending: Ending) {
        val names = endings.map { it.name }.toSet() + ending.name
        prefs.edit().putStringSet(KEY_ENDINGS, names).apply()
    }

    private companion object {
        const val KEY_DEATHS = "deaths"
        const val KEY_ENDINGS = "endings"
    }
}
