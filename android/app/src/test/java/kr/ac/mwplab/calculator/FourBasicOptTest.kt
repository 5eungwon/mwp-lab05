package kr.ac.mwplab.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class FourBasicOptTest {
    private val calculator = FourBasicOpt()
    @Test fun addsSignedDecimals() { assertEquals(-1.25, calculator.add(-2.5, 1.25), 0.0) }
    @Test fun subtracts() { assertEquals(-5.0, calculator.subtract(2.0, 7.0), 0.0) }
    @Test fun multiplies() { assertEquals(-6.0, calculator.multiply(-2.0, 3.0), 0.0) }
    @Test fun divides() { assertEquals(2.5, calculator.divide(5.0, 2.0), 0.0) }
    @Test fun divisionByZeroPreservesOriginalRule() {
        assertEquals(0.0, calculator.divide(5.0, 0.0), 0.0)
        assertEquals(0.0, calculator.divide(5.0, -0.0), 0.0)
    }
}
