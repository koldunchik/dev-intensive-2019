package ru.skillbranch.devintensive.utils

import android.content.Context
import android.util.TypedValue

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?> {
        var truncated : String = fullName?.trim()?.replace("\\s+".toRegex(), " ").orEmpty()
        var parts : List<String>? = truncated?.split(" ");

        var firstName = parts?.getOrNull(0)
        var lastName: String? = ""

        parts?.subList(1, parts.size)?.iterator()?.forEach { name -> run { lastName += " " + name} }

        when (firstName?.trim()) {
            "" -> firstName = null
        }

        when (lastName?.trim()) {
            "" -> lastName = null
        }

        return Pair(firstName?.trim(), lastName?.trim())
    }

    fun transliteration(payload: String, divider:String = " "):String {

        fun translite(input: String): String {
            val map = mapOf(
                "а" to "a",

                "б" to "b",

                "в" to "v",

                "г" to "g",

                "д" to "d",

                "е" to "e",

                "ё" to "e",

                "ж" to "zh",

                "з" to "z",

                "и" to "i",

                "й" to "i",

                "к" to "k",

                "л" to "l",

                "м" to "m",

                "н" to "n",

                "о" to "o",

                "п" to "p",

                "р" to "r",

                "с" to "s",

                "т" to "t",

                "у" to "u",

                "ф" to "f",

                "х" to "h",

                "ц" to "c",

                "ч" to "ch",

                "ш" to "sh",

                "щ" to "sh'",

                "ъ" to "",

                "ы" to "i",

                "ь" to "",

                "э" to "e",

                "ю" to "yu",

                "я" to "ya"
            );

            val mapUC = mutableMapOf<String, String>()

            for ((k, v) in map) {
                mapUC[k.toUpperCase()] = v.toUpperCase()
            }

            var result: String = ""
            for (i in input?.indices) {
                val c: String = input[i].toString();
                if (map.containsKey(c)) {
                    result += map.get(c);
                } else if (mapUC.containsKey(c)) {
                    result += mapUC.get(c);
                } else {
                    result += c;
                }
            }
            return result
        }

        var words : List<String>? = payload?.split(" ");

        if (words != null) {
            var result : String = ""
            for (i in 1..words.size) {
                result += translite(words[i-1])
                if (i<words.size) {
                    result += divider
                }
            }
            return result
        } else return ""

    }

    fun toInitials(firstName:String?, lastName:String?):String? {
        var result: String?
        var first: String? = firstName?.trim()?.getOrNull(0)?.toUpperCase()?.toString()
        var last: String? = lastName?.trim()?.getOrNull(0)?.toUpperCase()?.toString()
        if (first.isNullOrEmpty() && last.isNullOrEmpty()) return null;
        if (last.isNullOrEmpty()) return first.toString();
        if (first.isNullOrEmpty()) return last.toString();
        return "${first}${last}";
    }

    fun convertPxToDp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun convertDpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun convertSpToPx(context: Context, sp: Int): Int {
        return sp * context.resources.displayMetrics.scaledDensity.toInt()
    }

}