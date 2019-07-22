package ru.skillbranch.devintensive.models

class Bender
    (var status: Status = Status.NORMAL,
     var question: Question = Question.NAME)
{
    private var counter = 0

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus() : Status {
            when(this) {
                NORMAL -> return WARNING;
                WARNING -> return DANGER;
                DANGER -> return CRITICAL;
                CRITICAL -> return NORMAL;
            }
        }

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

    fun askQuestion():String = question.question

    fun listenAnswer(answer:String): Pair<String, Triple<Int, Int, Int>> {
        if (question.answers.contains(answer.trim().toLowerCase())) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            counter++
            counter%=4
            if (counter == 0) {
                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                return "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }
}