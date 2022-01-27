package com.hachitovhummus.androidapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hachitovhummus.androidapp.R

class Dish
{
    var nameBasicsPart by mutableStateOf("נקי")
    var nameSpecialPart by mutableStateOf("")
    var name: String = nameSpecialPart + nameBasicsPart
    var isSmallDish by mutableStateOf(false)
    var isSaladDish by mutableStateOf(false)
    var basics = mutableStateListOf<Basic>()
    var special by mutableStateOf(Special.NONE)
    var excludingSpices by mutableStateOf("")
    var imageID by mutableStateOf(R.drawable.no)

    var spices = mutableStateListOf<Spice>()
    init{
        spices.addAll(setOf(Spice.PAPRIKA, Spice.CUMIN, Spice.OLIVE_OIL, Spice.PARSLEY))
    }

    fun updateExcludingSpices(){
        val spicesToExclude = setOf(
            Spice.CUMIN,
            Spice.PAPRIKA,
            Spice.PARSLEY,
            Spice.OLIVE_OIL
        ).subtract(spices)

        excludingSpices = if(spicesToExclude.isEmpty()){
            ""
        } else{
            val spicesToExcludeTypes = mutableSetOf<String>()
            for(item: Spice in spicesToExclude){
                spicesToExcludeTypes.add(item.type)
            }
            replaceLastCommaWithAnd("בלי " + spicesToExcludeTypes.joinToString())
        }
    }

    fun updateNameBasicsPart(){
        nameBasicsPart = if (basics.isEmpty()) {
            ""
        } else {
            val basicsNames = mutableSetOf<String>()
            for(item: Basic in basics){
                basicsNames.add(item.type)
            }
            replaceLastCommaWithAnd(basicsNames.joinToString())
        }
    }

    private fun replaceLastCommaWithAnd(str: String): String {
        return if (str.contains(',')) {
            str.substringBeforeLast(',') + " ו" + str.substringAfterLast(", ")
        } else {
            str
        }
    }

    fun buildImageNameFromPrefixes(): String{
        var imageName: String = special.prefix
        if(basics.contains(Basic.CHICKPEA)){
            imageName += "_c"
        }
        if(basics.contains(Basic.TAHINI)){
            imageName += "_t"
        }
        if(basics.contains(Basic.FUL)){
            imageName += "_f"
        }
        if(basics.contains(Basic.EGG)){
            imageName += "_e"
        }

        return imageName
    }
}