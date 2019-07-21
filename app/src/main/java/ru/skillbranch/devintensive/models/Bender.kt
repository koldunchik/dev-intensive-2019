package ru.skillbranch.devintensive.models

class Bender {

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;
    }
    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")),
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")),
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")),
        BDAY("Когда меня создали?", listOf("2993")),
        SERIAL("Мой серийный номер?", listOf("2716057")),
        IDLE("На этом все, вопросов больше нет", listOf());

        fun nextQuestion() : Question {
            when(this) {
                NAME -> return PROFESSION;
                PROFESSION -> return MATERIAL;
                MATERIAL -> return BDAY;
                BDAY -> return SERIAL;
                SERIAL -> return IDLE;
                IDLE -> return IDLE;
            }
        }
    }

/*    fun listenAnswer(answer:String): Pair<String, Triple<Int, Int, Int>> {

    }*/

}