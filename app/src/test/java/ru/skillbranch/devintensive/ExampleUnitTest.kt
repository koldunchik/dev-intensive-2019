package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
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
        assertEquals("только что", Date().add(-1, TimeUnits.SECOND).humanizeDiff())

        assertEquals("несколько секунд назад",Date().add(-33, TimeUnits.SECOND ).humanizeDiff())
        assertEquals("через несколько секунд", Date().add(+33, TimeUnits.SECOND).humanizeDiff())

        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через час", Date().add(1, TimeUnits.HOUR).humanizeDiff())

        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 2 часа", Date().add(2, TimeUnits.HOUR).humanizeDiff())

        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 5 часов", Date().add(5, TimeUnits.HOUR).humanizeDiff())

        assertEquals("21 час назад", Date().add(-21, TimeUnits.HOUR).humanizeDiff())
        assertEquals("через 21 час", Date().add(21, TimeUnits.HOUR).humanizeDiff())


        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("через день", Date().add(1, TimeUnits.DAY).humanizeDiff())

        assertEquals("2 дня назад", Date().add(-2, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 дня", Date().add(2, TimeUnits.DAY).humanizeDiff())

        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 5 дней", Date().add(5, TimeUnits.DAY).humanizeDiff())

        assertEquals("21 день назад", Date().add(-21, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 21 день", Date().add(21, TimeUnits.DAY).humanizeDiff())

        assertEquals("через минуту", Date().add(1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("через 5 минут", Date().add(5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("через 21 минуту", Date().add(21, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("21 минуту назад", Date().add(-21, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("через день", Date().add(1, TimeUnits.DAY).humanizeDiff())

        assertEquals("через 2 дня", Date().add(2, TimeUnits.DAY).humanizeDiff())
        assertEquals("2 дня назад", Date().add(-2, TimeUnits.DAY).humanizeDiff())

        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("7 дней назад", Date().add(-7, TimeUnits.DAY).humanizeDiff())

        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())

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


    @Test
    fun test_initials() {
        assertEquals("JD", Utils.toInitials("john", "doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertNull(Utils.toInitials(null, null))
        assertNull(Utils.toInitials(" ", ""))

        assertEquals("J", Utils.toInitials("john", " "))
        assertEquals("B", Utils.toInitials(" ", "boss"))

    }

    @Test
    fun test_builder() {
        var u: User = User.Builder().id("1").firstName("J").lastName("Doc").isOnline(false).build()
    }

    @Test
    fun test_plural() {
        assertEquals("1 секунду", TimeUnits.SECOND.plural(1))
        assertEquals("4 минуты", TimeUnits.MINUTE.plural(4))
        assertEquals("19 часов", TimeUnits.HOUR.plural(19))
        assertEquals("222 дня", TimeUnits.DAY.plural(222))
        assertEquals("365 дней", TimeUnits.DAY.plural(365))
    }

    @Test
    fun test_truncate() {
        //extension усекающий исходную строку до указанного числа символов(по умолчанию 16) и возвращающий усеченную строку с заполнителем "..."
        // (если строка была усечена) если последний символ усеченной строки является пробелом-удаляет его и добавляет заполнитель
        //Пример:

        assertEquals("Bender Bending R...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        assertEquals("Bender Bending...",   "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        assertEquals("A  ", "A     ".truncate(3))

        assertEquals("A", "A".truncate(1))
        assertEquals("A", "AB".truncate(1))
        assertEquals("AB", "AB".truncate(2))
        assertEquals("ABC", "ABC".truncate(3))

        assertEquals("ABC...", "ABCD".truncate(3))
        assertEquals("ABCDEF...", "ABCDEFG".truncate(6))

        assertEquals("ABCDEF...", "ABCDEFGHASIDJASIDJIASJDIASJDIDJ".truncate(6))
        assertEquals("A B...", "A B C D E".truncate(3))
        assertEquals(" B...", " B  C D E".truncate(3))
   }

    @Test
    fun test_strip_html() {
//    extension позволяющий очистить строку от html тегов и html escape последовательностей ("& < > ' ""), а так же удалить пустые символы (пробелы) между словами если их больше 1.
//    Пример:
//    "<p class="title">Образовательное IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
//    "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch

        assertEquals("Образовательное IT-сообщество Skill Branch", "<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p>Образовательное       &amp; IT-сообщество Skill &amp; Branch</p>".stripHtml())
        assertEquals("ABC", "A&amp;&quot;&gt;&lt;&apos;B&amp;C".stripHtml())

    }


    @Test
    fun test_bender() {
        var q = Bender.Question.NAME
        assertEquals("Как меня зовут?", q.question)
        assertEquals("Назови мою профессию?", q.nextQuestion().question)
        assertEquals("На этом все, вопросов больше нет", q.nextQuestion().nextQuestion().nextQuestion().nextQuestion().nextQuestion().question)

    }

}
