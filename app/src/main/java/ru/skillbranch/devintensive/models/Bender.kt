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
        NAME("Как меня зовут?", listOf("Бендер", "Bender")),
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
        if (question.answers.contains(answer.trim())) {
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            counter++
            counter%=4
            if (counter == 0) {
                status = Status.NORMAL
                question = Question.NAME
                return "${checkAnswer(answer)}. Давай все по новой\n${question.question}" to status.color
            } else {
                return "${checkAnswer(answer)}\n${question.question}" to status.color
            }
        }
    }

    fun checkAnswer(answer:String) : String {
        if (isValidAnswer(answer) || Question.IDLE.equals(question)) {
            return "Это неправильный ответ"
        } else {
            return invalidAnswerDescription(answer);
        }
    }

    fun isValidAnswer(answer:String) : Boolean {
        when(question) {
            Question.NAME -> return answer.trim().firstOrNull()?.isUpperCase() ?: false;
            Question.PROFESSION -> return answer.trim().firstOrNull()?.isLowerCase() ?: false;
            Question.MATERIAL -> return answer.trim().contains(Regex("\\d")).not();
            Question.BDAY -> return answer.trim().contains(Regex("^[0-9]*$"));
            Question.SERIAL -> return answer.trim().contains(Regex("^[0-9]{7}$"));
            Question.IDLE -> return true;
        }
    }

    fun invalidAnswerDescription(answer:String) : String {
        when (question) {
            Question.NAME -> return "Имя должно начинаться с заглавной буквы"
            Question.PROFESSION -> return "Профессия должна начинаться со строчной буквы"
            Question.MATERIAL -> return "Материал не должен содержать цифр"
            Question.BDAY -> return "Год моего рождения должен содержать только цифры"
            Question.SERIAL -> return "Серийный номер содержит только цифры, и их 7"
            Question.IDLE -> return "" //игнорировать валидацию
        }
    }
}