package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.models.User

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
        val userLong = User.makeUser("One Two Three")
        assertEquals(userLong.firstName, "One")
        assertEquals(userLong.lastName, "Two Three")

        val userMiddle = User.makeUser("One Two")
        assertEquals(userMiddle.firstName, "One")
        assertEquals(userMiddle.lastName, "Two")

        val userShort = User.makeUser("One")
        assertEquals(userShort.firstName, "One")
        assertEquals(userShort.lastName, "")

        val userNothing = User.makeUser(null)
        assertEquals(userNothing.firstName, "")
        assertEquals(userNothing.lastName, "")

        val userEmpty = User.makeUser(" ")
        assertEquals(userEmpty.firstName, "")
        assertEquals(userEmpty.lastName, "")
    }
}
