package com.hachitovhummus.androidapp.model

import com.hachitovhummus.androidapp.R

class Dish(
    var nameBasicsPart: String = "נקי",
    var nameSpecialPart: String = "",
    var name: String = nameSpecialPart + nameBasicsPart,
    var excludingSpices: String = "",
    var special: Special = Special.NONE,
    var basics: Set<Basic> = setOf(),
    var enabledBasics: Set<Basic> = setOf(),
    var spices: Set<Spice> = setOf(Spice.PAPRIKA, Spice.CUMIN, Spice.OLIVE_OIL, Spice.PARSLEY),
    var isSmallDish: Boolean = false,
    var isSaladDish: Boolean = false,
    var imageID: Int = R.drawable.no) {
}