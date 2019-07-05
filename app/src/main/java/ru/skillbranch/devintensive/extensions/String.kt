package ru.skillbranch.devintensive.extensions

const val dots = "..."


fun String.truncate(len : Int = 16): String {
    val oldLength = this.length
    if (oldLength < len || oldLength < 3) {
        return this
    }

    var truncated:String = this.substring(0, len)
    var cut:String = this.substring(len + 1, this.length - 1).trim()

    if (cut.isNullOrEmpty()) {
        return truncated;
    }

    val lastChar: String = truncated[truncated.lastIndex].toString()

    when (lastChar) {
        " " -> {
            truncated = truncated.substring(0, truncated.length - 1)
        }
    }

    truncated += dots

    return truncated
}
