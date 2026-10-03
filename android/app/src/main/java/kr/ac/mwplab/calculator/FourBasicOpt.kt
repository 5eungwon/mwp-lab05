package kr.ac.mwplab.calculator

class FourBasicOpt {
    fun add(x: Double, y: Double) = x + y
    fun subtract(x: Double, y: Double) = x - y
    fun multiply(x: Double, y: Double) = x * y
    fun divide(x: Double, y: Double) = if (y == 0.0) 0.0 else x / y
}
