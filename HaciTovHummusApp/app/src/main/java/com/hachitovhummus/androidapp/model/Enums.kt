package com.hachitovhummus.androidapp.model

import androidx.compose.ui.graphics.Color
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.ui.theme.*

enum class Basic(val type: String, val imageID: Int ,val backgroundColor: Color) {
    CHICKPEA("גרגירים", R.drawable.chikpea, Cream3),
    TAHINI("טחינה", R.drawable.tahini, Green1),
    EGG("ביצה", R.drawable.egg, Yellow1),
    FUL("פול", R.drawable.ful, Brown1);
}

enum class Special(val type: String, val imageID: Int, val gradientColors: List<Color>, val prefix: String, val isSalad: Boolean, val enabledBasics: Set<Basic>, val additionalPrice: Int) {
    NONE("קלאסי", R.drawable.classic, listOf(Yellow5,Yellow6), "no", false, setOf(Basic.CHICKPEA, Basic.TAHINI, Basic.FUL, Basic.EGG), 0),
    HAMSHUKA("חמשוקה", R.drawable.shakshuka, listOf(Orange1,Orange2), "sh", false, setOf(Basic.CHICKPEA, Basic.TAHINI), 6),
    MUSHROOMS("פטריות", R.drawable.mushrooms, listOf(Brown2,Brown3), "mu", false, setOf(Basic.CHICKPEA, Basic.TAHINI, Basic.EGG), 6),
    MSABAHA("מסבחה", R.drawable.msabaha, listOf(Gold1,Gold2), "ms", false, setOf(Basic.FUL, Basic.EGG), 0),
    BIG_SALAD("סלט גדול", R.drawable.big_salad, listOf(Red3,Red4), "bs", true, setOf(Basic.EGG), 20),
    SPECIAL_SALAD("סלט פינוקים", R.drawable.special_salad, listOf(Green1,Green2), "ss", true, setOf(Basic.EGG), 25);
}

enum class Spice(val type: String, val imageID: Int, val backgroundColor: Color) {
    CUMIN("כמון", R.drawable.cumin, Cream4),
    PAPRIKA("פפריקה", R.drawable.paprika, Red1),
    OLIVE_OIL("שמן זית", R.drawable.olive_oil, Yellow2),
    PARSLEY("פטרוזיליה", R.drawable.parsley, Green3);
}

enum class Drink(val type: String, val imageID: Int, val backgroundColor: Color) {
    NONE("", R.drawable.coke_zero, Color.White),
    WATER("מים", R.drawable.water, Blue1),
    COKE("קולה", R.drawable.coke, Red2),
    COKE_ZERO("קולה זירו", R.drawable.coke_zero, Grey2),
    SPRITE("ספרייט", R.drawable.sprite, Green3),
    SPRITE_ZERO("ספרייט זירו", R.drawable.sprite_zero, Yellow4),
    FANTA("פאנטה", R.drawable.fanta, Orange1),
    GRAPE_JUICE("ענבים", R.drawable.grape_juice, Purple1),
    SODA("סודה", R.drawable.soda, Purple2);
}