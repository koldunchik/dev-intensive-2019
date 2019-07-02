package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val SECOND = 1
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun Date.format(thePattern:String = "HH:mm:ss dd.MM.yy"): String{
    val sdf= SimpleDateFormat(thePattern, Locale("ru"))
    return sdf.format(this)
}

fun Date.add(field: Int, amount: Int): Date{
    val cal = Calendar.getInstance()
    cal.time=this
    cal.add(field, amount)
    this.time = cal.time.time
    cal.clear()
    return this
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    when (units) {
        TimeUnits.SECOND -> return add(Calendar.SECOND, value)
        TimeUnits.MINUTE -> return add(Calendar.MINUTE, value)
        TimeUnits.HOUR -> return add(Calendar.HOUR_OF_DAY,value)
        TimeUnits.DAY -> return add(Calendar.DAY_OF_MONTH, value)
    }
}

fun hoursSuffix(hours: Int):String {
    var res = "$hours "
    when (hours % 10) {
        1 -> res+="час"
        in 2..4 -> res+="часа"
        else -> res+="часов"
    }
    return res
}

fun daysSuffix(value: Int):String {
    var res = "$value "
    when (value % 10) {
        1 -> res+="день"
        in 2..4 -> res+="дня"
        else -> res+="дней"
    }
    return res
}

fun minutesSuffix(value: Int):String {
    var res = "$value "
    when (value % 10) {
        1 -> res+="минуту"
        in 2..4 -> res+="минуты"
        else -> res+="минут"
    }
    return res
}


fun Date.humanizeDiff(date: Date) : String {
    val diffInMs = this.getTime() - date.getTime()
    var delta : Int = TimeUnit.MILLISECONDS.toSeconds(diffInMs).toInt()
    val sign = delta

    fun addVerbal(input: String):String {
        if (sign>=0) {
            return "$input назад"
        } else {
            return "через $input"
        }
    }

    delta = abs(delta)

    when (delta) {
        0,1 -> return "только что"
        in 1..45 -> return addVerbal("несколько секунд")
        in 45..75 -> return addVerbal("минуту")
        in 75 .. 45*60  -> return addVerbal(minutesSuffix((delta/60).toInt()))
        in 45*60 + 1 .. 75*60 -> return addVerbal("час")
        in 75*60 + 1 .. 22*60*60 -> return addVerbal(hoursSuffix((delta/3600).toInt()))
        in 22*60*60 + 1 .. 26*60*60 -> return addVerbal("день")
        in 26*60*60 + 1 .. 360*24*60*60 -> return addVerbal(daysSuffix((delta/(3600*24)).toInt()))
        else -> if (sign < 0) return "более чем через год" else return "более года назад"
    }

    return ""
}

