@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson1.task1.sqr

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    companion object {
        private fun checkIm(s: String): Double {
            return if (s == "i") 1.0
            else s.dropLast(1).toDouble()
        }

        fun splitForReIm(s: String): Pair<Double, Double> {
            if (s == "") return 0.0 to 0.0
            var plus = 0
            var minus = 0
            for (simbol in s) {
                if (simbol == '+') plus++
                if (simbol == '-') minus++
            }
            val list: List<String>
            when {
                plus == 1 -> {
                    list = s.split('+')
                    return list[0].toDouble() to checkIm(list[1])
                }
                minus == 2 -> {
                    list = s.split('-')
                    return (-1) * list[1].toDouble() to (-1) * checkIm(list[2])
                }
                minus == 1 -> {
                    if (s.last() != 'i') return s.toDouble() to 0.0
                    list = s.split('-')
                    if (list[0] == "") return 0.0 to (-1) * checkIm(list[1])
                    return list[0].toDouble() to (-1) * checkIm(list[1])
                }
                else -> {
                    if (s.last() != 'i') return s.toDouble() to 0.0
                    if (s == "i") return 0.0 to 1.0
                    return 0.0 to s.dropLast(1).toDouble()
                }
            }
        }
    }

    constructor(s: String) : this(splitForReIm(s).first, splitForReIm(s).second)


    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(other.re + this.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-this.im, -this.re)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(this.re - other.re, this.im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(this.re * other.re - this.im * this.im + this.re * other.im + other.re * this.im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        val de = other.re * other.re + other.im * other.im
        return Complex(
            (this.re * other.re + this.im * other.im) /
                    de, (this.im * other.re - this.re * other.im) / de
        )
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Complex) return false
        return re == other.re && im == other.im
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String =
        when {
            im == 0.0 && re == 0.0 -> "0.0"
            re == 0.0 -> "${im}i"
            im == 0.0 -> "$re"
            im == 1.0 -> "${re}+i"
            im == -1.0 -> "${re}-i"
            else -> if (im > 0) "${re}+${im}i" else "${re}${im}i"
        }

}
