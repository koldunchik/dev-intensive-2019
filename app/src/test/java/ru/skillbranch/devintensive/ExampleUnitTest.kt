package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.format
import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user = User("2", "First","Second")
    }

    @Test
    fun test_factory(){
        var user = User.makeUser(null)
        assertNull(user.firstName)
        assertNull(user.lastName)

        user = User.makeUser("")
        assertNull(user.firstName)
        assertNull(user.lastName)

        user = User.makeUser(" ")
        assertNull(user.firstName)
        assertNull(user.lastName)

        user = User.makeUser("One")
        assertEquals("One" ,user.firstName)
        assertNull(user.lastName)

        user = User.makeUser("One Two")
        assertEquals("One", user.firstName)
        assertEquals("Two", user.lastName )

        user = User.makeUser("    One    Two    Three")
        assertEquals("One", user.firstName)
        assertEquals("Two Three", user.lastName)

    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Василий");
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), payload ="any text", type="text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), payload ="any text", type="image")

        when(txtMessage) {
            is BaseMessage -> println("this is base")
            is TextMessage -> println("this is text")
            is ImageMessage -> println("this is image")
        }


        val txt1:String = BaseMessage.makeMessage(user, Chat("0"), Date(),                               "text",  "any text message").formatMessage()
        val txt2:String = BaseMessage.makeMessage(user, Chat("0"), Date().add(-2, TimeUnits.HOUR), "image", "https://anyurl.com",true).formatMessage()

        assertEquals("Василий отправил сообщение \"any text message\" только что", txt1)
        assertEquals("Василий получил изображение \"https://anyurl.com\" 2 часа назад", txt2)

    }

    @Test
    fun test_dates() {
        println(Date().format())
        println(Date().add(2, TimeUnits.SECOND).format())
        println(Date().add(-2, TimeUnits.SECOND).format())
        println(Date().add(-4, TimeUnits.DAY).format())
    }

    @Test
    fun test_humanize_diff(){
        assertEquals("только что", Date().humanizeDiff(Date().add(-1, TimeUnits.SECOND)))
        assertEquals("несколько секунд назад", Date().humanizeDiff(Date().add(-33, TimeUnits.SECOND)))
        assertEquals("2 часа назад", Date().humanizeDiff(Date().add(-2, TimeUnits.HOUR)))
        assertEquals("5 дней назад", Date().humanizeDiff(Date().add(-5, TimeUnits.DAY)))
        assertEquals("через 2 минуты", Date().humanizeDiff(Date().add(2, TimeUnits.MINUTE)))
        assertEquals("через 7 дней", Date().humanizeDiff(Date().add(7, TimeUnits.DAY)))
        assertEquals("более года назад", Date().humanizeDiff(Date().add(-400, TimeUnits.DAY)))
        assertEquals("более чем через год", Date().humanizeDiff(Date().add(400, TimeUnits.DAY)))

    }

    @Test
    fun test_transliteration() {
        assertEquals("Ivan Stereotipov", Utils.transliteration("Иван Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр", "_"))
        assertEquals("Privet mir", Utils.transliteration("Привет мир"))
        assertEquals("    Privet    mir   ", Utils.transliteration("    Привет    мир   "))
        assertEquals("pRIvet mir", Utils.transliteration("pRIвет мир"))
        assertEquals("PRIvet Mir", Utils.transliteration("PRIвет Mир"))
        assertEquals("PRIvet1345 Mir", Utils.transliteration("PRIвет1345 Mир"))
        assertEquals("[]{}PRIvet Mir/", Utils.transliteration("[]{}PRIвет Mир/"))
        assertEquals("[]{}PRIvet____Mir/", Utils.transliteration("[]{}PRIвет    Mир/", "_"))
        assertEquals("[_444__444__444__444_]{}PRIvet_444__444_Mir/", Utils.transliteration("[    ]{}PRIвет  Mир/", "_444_"))
    }




}
