package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?> {
        var parts : List<String>? = fullName?.split(" ");

        var firstName = parts?.getOrNull(0)?.toString().orEmpty();
        var lastName = parts?.getOrNull(1)?.toString().orEmpty()
        var thirdName = parts?.getOrNull(2)?.toString().orEmpty();
        if (thirdName.isNotEmpty()) {
            lastName = lastName + " " + thirdName
        }

        return Pair(firstName, lastName)
    }
}