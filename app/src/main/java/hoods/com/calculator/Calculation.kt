package hoods.com.calculator

import kotlin.math.pow

class InfixToPostFix {

    private fun notNumeric(ch: Char): Boolean = when (ch) {
        '+', '-', '*', '/', '(', ')', '^' -> true
        else -> false
    }

    private fun operatorPrecedence(ch: Char): Int = when (ch) {
        '+', '-' -> 1
        '*', '/' -> 2
        '^' -> 3
        else -> -1
    }


    fun postFixConversion(string: String): String {
        var result = ""

        val st = ArrayDeque<Char>()

        for (s in string) {

            if (!notNumeric(s)) {
                result += s
            } else if (s == '(') {
                st.push(s)
            } else if (s == ')') {
                while (!st.isEmpty() && st.peek() != '(') {
                    result += " " + st.pop()
                }
                st.pop()
            } else {

                while (
                    !st.isEmpty()
                    && operatorPrecedence(s) <= operatorPrecedence(st.peek()!!)
                ) {
                    result += " ${st.pop()} "
                }
                st.push(s)
                result += " "


            }


        }

        result += " "
        while (!st.isEmpty()) {
            if (st.peek() == '(') return "Error"

            result += st.pop()!! + " "


        }

        return result.trim()


    }


}

class Model {

    private fun replaceN(string: String): String {
        val array = StringBuffer(string)

        if (array[0] == '-') {
            array.setCharAt(0, 'n')
        }

        var i = 0
        while (i < array.length) {

            if (array[i] == '-') {
                if (
                    array[i - 1] == '+' ||
                    array[i - 1] == '-' ||
                    array[i - 1] == '/' ||
                    array[i - 1] == '*' ||
                    array[i - 1] == '('
                ) {
                    array.setCharAt(i, 'n')
                }
            }

            i++

        }

        return array.toString()

    }

    fun result(string: String): String {
        val stringN = replaceN(string)
        val postFix = InfixToPostFix().postFixConversion(stringN)

        if (postFix == "Error") {
            return postFix
        }
        return try {
            val evaluation = ArithmeticEvaluation().evaluation(postFix)
            evaluation.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }


    }

}

class ArithmeticEvaluation {
    private fun notOperator(ch: Char): Boolean = when (ch) {
        '+', '-', '*', '/', '(', ')', '^' -> false
        else -> true
    }
    
    fun evaluation(string: String): Double {
        var str = ""

        val stack = ArrayDeque<Double>()

        for (ch in string) {

            if (notOperator(ch) && ch != ' ') {
                str += ch
            } else if (ch == ' ' && str != "") {
                stack.push(str.replace('n', '-').toDouble())
                str = ""
            } else if (!notOperator(ch)) {
                val val1 = stack.pop()
                val val2 = stack.pop()

                when (ch) {
                    '+' -> stack.push(val2!! + val1!!)
                    '-' -> stack.push(val2!! - val1!!)
                    '/' -> stack.push(val2!! / val1!!)
                    '*' -> stack.push(val2!! * val1!!)
                    '^' -> stack.push(val2!!.pow(val1!!))


                }


            }


        }


        return stack.pop()!!


    }


}


fun <T> ArrayDeque<T>.push(element: T) = addLast(element)
fun <T> ArrayDeque<T>.pop() = removeLastOrNull()
fun <T> ArrayDeque<T>.peek() = lastOrNull()




















