package ru.skillbranch.devintensive.extensions

const val dots = "..."


fun String.truncate(len : Int = 16): String {
    val oldLength = this.length
    if (oldLength < len) {
        return this
    }

    if (len < 3) {
        return  this.substring(0, len)
    }

    var truncated:String = this.substring(0, len)
    var cut:String = this.substring(len, this.length).trim()

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

fun String.stripHtml(): String {
    var result: String = this
    var cleanHtml: String = result.replace("\\<[^>]*>".toRegex(),"")
    var strip: String = cleanHtml.replace("\\s+".toRegex(), " ")
    strip = strip.replace("&amp;".toRegex(),"&")
    strip = strip.replace("&gt;".toRegex(),">")
    strip = strip.replace("&lt;".toRegex(),"<")
    strip = strip.replace("&quot;".toRegex(),"\"")
    strip = strip.replace("&apos;".toRegex(),"'")
    return strip
}