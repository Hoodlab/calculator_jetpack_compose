package hoods.com.calculator

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

fun <T> ArrayDeque<T>.push(element: T) = addLast(element)
fun <T> ArrayDeque<T>.pop() = removeLastOrNull()
fun <T> ArrayDeque<T>.peek() = lastOrNull()




















