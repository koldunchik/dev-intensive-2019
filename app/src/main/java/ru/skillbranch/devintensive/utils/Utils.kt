package ru.skillbranch.devintensive.utils

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
}