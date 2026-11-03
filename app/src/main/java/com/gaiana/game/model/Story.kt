package com.gaiana.game.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.gaiana.game.R

enum class SceneId {
    ARBAT, PROITI, KENEF, VODA, GAIANA,
    ANGEL, ANGEL2, ANGEL3, ORBITALI, METAL, TECHNO,
    GESHEV1, GESHEV2, GESHEV3, ISTORICHKA,
    DIE, FINALE,
}

/** How a scene behaves beyond "show picture, play voice, offer choices". */
enum class SceneKind { STORY, DEATH, FINALE }

/** Every way the game can end. Order is the order shown in the endings list. */
enum class Ending(val title: String) {
    POZHALUISTA("Пожалуйста, я нуждаюсь"),
    SPASQ("Искам да я спася"),
    FIZIK("Прекъсна историчката"),
    ISTORICHKA("Остана да слушаш"),
    METAL("Изкуствено дишане (метал)"),
    TECHNO("Изкуствено дишане (техно)"),
    GESHEV("Приближи се да погледнеш"),
    RUSKATA("Спаси руската"),
    KABINET31("Към 31-ви кабинет"),
}

class Choice(
    val label: String,
    /** Where this choice leads; null means it goes nowhere (the fake "exit"). */
    val to: SceneId?,
    /** Played the moment the choice is tapped, and keeps playing into the next scene. */
    @RawRes val sound: Int? = null,
    /** How long to linger before moving on (usually the length of [sound]). */
    val delayMs: Long = 0,
    /** Text the tapped button shows while lingering. */
    val caption: String? = null,
    /** Image laid over the scene while lingering. */
    @DrawableRes val overlay: Int? = null,
    val hideTitle: Boolean = false,
    val ending: Ending? = null,
)

/** A clip on the finale screen: tapping it plays the sound and shows the note. */
class Extra(val label: String, @RawRes val sound: Int, val note: String)

class Scene(
    val id: SceneId,
    val title: String,
    @DrawableRes val image: Int? = null,
    @RawRes val video: Int? = null,
    /** Narration started on entry; null keeps whatever is already playing. */
    @RawRes val voice: Int? = null,
    val titleSp: Float = 60f,
    @ColorRes val titleColor: Int = R.color.white,
    val choices: List<Choice> = emptyList(),
    val kind: SceneKind = SceneKind.STORY,
    /** Played instead of nothing when the player presses Back here. */
    @RawRes val backSound: Int? = null,
)

object Story {
    val start = SceneId.ARBAT

    private val all = listOf(
        Scene(
            SceneId.ARBAT, "В часа по руски при Ирина Тончева... ",
            video = R.raw.teacher1, voice = R.raw.a0,
            choices = listOf(
                Choice("иди об кенефом", SceneId.KENEF),
                Choice("Пройти до тоалетну", SceneId.PROITI),
            ),
        ),
        Scene(
            SceneId.PROITI, "Говори по-убедително !!!",
            image = R.drawable.staq, voice = R.raw.c1, titleColor = R.color.black,
            choices = listOf(
                Choice("Пожалуйста я нуждаюсь", SceneId.DIE, sound = R.raw.c2end, ending = Ending.POZHALUISTA),
                Choice("Иди об Кенефом", SceneId.KENEF),
            ),
        ),
        Scene(
            SceneId.KENEF, "По път към тоалетната...",
            image = R.drawable.koridor, voice = R.raw.d1, titleSp = 70f, titleColor = R.color.black,
            choices = listOf(
                Choice("ще пиеш ли вода", SceneId.VODA),
                Choice("ще се върнеш в стаята", SceneId.ANGEL),
            ),
        ),
        Scene(
            SceneId.VODA, "Докато си пиех вода...",
            image = R.drawable.toilet, voice = R.raw.gaiana810, titleSp = 70f, titleColor = R.color.black,
            choices = listOf(
                Choice("Фантазирам ли...?", SceneId.ARBAT, sound = R.raw.buttonanta, delayMs = 5_000),
                Choice("Не не фантазирам", SceneId.GAIANA),
            ),
        ),
        Scene(
            SceneId.GAIANA, "Да спася руската, или да не спася руската?",
            image = R.drawable.gaiana, voice = R.raw.button15, titleColor = R.color.light_red,
            choices = listOf(
                Choice(
                    "съвсем не искам", SceneId.ANGEL, sound = R.raw.button22no, delayMs = 25_000,
                    caption = "ами засега избяга...", overlay = R.drawable.angelgun, hideTitle = true,
                ),
                Choice(
                    "спасявай си я сам", SceneId.ANGEL3, sound = R.raw.button27rlyno, delayMs = 22_000,
                    caption = "ами засега избяга...", hideTitle = true,
                ),
                Choice("ИСКАМ ДА Я СПАСЯ", SceneId.DIE, sound = R.raw.button21, ending = Ending.SPASQ),
            ),
        ),
        Scene(
            SceneId.ANGEL, "А междувремено в празната стая...",
            image = R.drawable.angel, voice = R.raw.angel, titleSp = 40f,
            choices = listOf(
                Choice("фантазирам ли...?", SceneId.ARBAT, sound = R.raw.buttonanta, delayMs = 5_000),
                Choice("НЕ, не фантазирам!", SceneId.ANGEL2),
            ),
        ),
        Scene(
            SceneId.ANGEL2, "Предстои ти важен избор...",
            image = R.drawable.angel, voice = R.raw.button30, titleSp = 40f,
            choices = listOf(
                Choice(
                    "писна ми тая тъпотия - exit!", null, sound = R.raw.button33, delayMs = 3_000,
                    caption = "... помисли си че е толкова лесно ли?",
                ),
                Choice("спасявам руската в крайна сметка...!", SceneId.FINALE, ending = Ending.RUSKATA),
                Choice("бягам за помощ при директора", SceneId.ORBITALI),
            ),
        ),
        Scene(
            SceneId.ANGEL3, "В празната стая...",
            image = R.drawable.angel, voice = R.raw.angel3, titleSp = 50f,
            choices = listOf(
                Choice("отивам към директорската стая", SceneId.ORBITALI),
                Choice("ще послушам ангелчето", SceneId.ISTORICHKA),
            ),
        ),
        Scene(
            SceneId.ORBITALI, "Преминавайки през кабинета по химия...",
            image = R.drawable.koridor, voice = R.raw.orbitali,
            choices = listOf(
                Choice("например .. Метал!", SceneId.METAL),
                Choice("ами например ... техно!!!", SceneId.TECHNO),
            ),
        ),
        Scene(
            SceneId.METAL, "Май това не беше най-правилния избор...",
            image = R.drawable.metal, voice = R.raw.direktormetal, titleSp = 50f,
            choices = listOf(
                Choice("изкуствено дишане", SceneId.DIE, sound = R.raw.direktorend, ending = Ending.METAL),
                Choice("ще зарежа трупа и си тръгвам", SceneId.GESHEV1),
            ),
        ),
        Scene(
            SceneId.TECHNO, "Май това не беше най-правилния избор...",
            image = R.drawable.techno, voice = R.raw.direktortehno,
            choices = listOf(
                Choice("изкуствено дишане", SceneId.DIE, sound = R.raw.direktorend, ending = Ending.TECHNO),
                Choice("ще зарежа трупа и си тръгвам", SceneId.GESHEV1),
            ),
        ),
        Scene(
            SceneId.GESHEV1, "Какво очакваш да види Гешев в стаята?",
            image = R.drawable.geshev1, voice = R.raw.geshev1, titleSp = 50f, titleColor = R.color.black,
            choices = listOf(
                Choice("мъртвата директорка може би", SceneId.GESHEV2),
                Choice("Няма да види никой...", SceneId.GESHEV3),
            ),
        ),
        Scene(
            SceneId.GESHEV2, "Вътре в директорската стая...",
            image = R.drawable.praznastaq, voice = R.raw.geshev2,
            choices = listOf(
                Choice("ще се приближиш да погледнеш...", SceneId.DIE, sound = R.raw.geshevend, ending = Ending.GESHEV),
                Choice("ще си тръгнеш набързо...", SceneId.ISTORICHKA),
            ),
        ),
        Scene(
            SceneId.GESHEV3, "Отмаза се от Гешев, а сега накъде...",
            image = R.drawable.praznastaq, voice = R.raw.geshev3, titleSp = 50f,
            choices = listOf(
                Choice("по коридора и извън училището", SceneId.ISTORICHKA),
                Choice("към 31-ви кабинет!!!", SceneId.FINALE, ending = Ending.KABINET31),
            ),
        ),
        Scene(
            SceneId.ISTORICHKA, "Докато ходиш по коридора...",
            image = R.drawable.koridor, voice = R.raw.istorichka1, backSound = R.raw.istorichkafalse,
            choices = listOf(
                Choice("... ще я прекъснеш и продължиш", SceneId.DIE, sound = R.raw.fizikend, ending = Ending.FIZIK),
                Choice("ще останеш да слушаш...", SceneId.DIE, sound = R.raw.istorichkaend, ending = Ending.ISTORICHKA),
            ),
        ),
        Scene(
            SceneId.DIE, "Ами сега? Стана тя каквато стана...",
            image = R.drawable.question, video = R.raw.video, kind = SceneKind.DEATH,
            choices = listOf(Choice("нова игра?", start)),
        ),
        Scene(
            SceneId.FINALE, "",
            video = R.raw.finalactv, voice = R.raw.finalact, kind = SceneKind.FINALE,
            choices = listOf(Choice("нова игра?", start)),
        ),
    ).associateBy { it.id }

    operator fun get(id: SceneId): Scene = all.getValue(id)

    const val DEATH_REVEAL_MS = 10_000L
    const val DEATH_TITLE = "За съжаление това беше твоят край ..."
    const val FINALE_REVEAL_MS = 9_000L
    const val FINALE_NOTE = "Поздравления, ти изгуби с чест играта"

    val outtakes = listOf(
        Extra("ГАФ 1", R.raw.gaf2, "насладете се на нашата колекция от гафове"),
        Extra("ГАФ 2", R.raw.gaf3, "играта е оригинално правена през 1999 година"),
        Extra("ГАФ 3", R.raw.gaf5, "оригиналната игра е създадена в рамките на няколко месеца"),
        Extra("ГАФ 4", R.raw.gaf6, "основно разчитахме на мозъчните припръдвания на Цоцика"),
        Extra("ГАФ 5", R.raw.gaf7, "играта е преработвана веднъж за уиндоус през 2018 година"),
        Extra("ГАФ 6", R.raw.gaf8, "играта е недовършена, оригинално се очакваше да е много по дълга"),
        Extra("ГАФ 7", R.raw.gaf9, "последните няклко епизода са правени месеци по късно"),
        Extra("ГАФ 8", R.raw.gaf10, "оригинално е замислена да няма положителен завършек"),
        Extra("ГАФ 9", R.raw.gaf15, "благодарим ви че изслушахте мозъчните ни пръдни отпреди 20 години..."),
    )
}
