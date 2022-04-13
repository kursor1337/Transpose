import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixTest {


    val expectedSimple = Matrix(
        mutableListOf(
            mutableListOf("1", "1", "1", "", ""),
            mutableListOf("1", "1", "1", "1", "1"),
            mutableListOf("1", "1", "1", "1", ""),
            mutableListOf("1", "1", "", "", "")
        )
    )

    @Test
    fun constructor() {
        val result1 = Matrix(
            mutableListOf(
                mutableListOf("1", "1", "1"),
                mutableListOf("1", "1", "1", "1", "1"),
                mutableListOf("1", "1", "1", "1"),
                mutableListOf("1", "1")
            )
        )
        assertEquals(expectedSimple, result1)

        val result2 = Matrix(
            mutableListOf(
                mutableListOf("1", "1", "1", "1"),
                mutableListOf("1", "1", "1", "1"),
                mutableListOf("1", "1", "1", ""),
                mutableListOf("", "1", "1", ""),
                mutableListOf("", "1", "", "")
            ), transposed = true
        )
        assertEquals(expectedSimple, result2)
    }

    @Test
    fun fromString() {
        val result = Matrix(
            """
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent()
        )

        assertEquals(expectedSimple, result)
    }

    @Test
    fun toStringTest() {
        assertEquals(
            """
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent(), expectedSimple.toString()
        )
    }

    @Test
    fun fromToString() {
        assertEquals(expectedSimple, Matrix(expectedSimple.toString()))
    }

    @Test
    fun toFromString() {
        assertEquals(expectedSimple.toString(), Matrix(expectedSimple.toString()).toString())
    }

    @Test
    fun num() {
        val result = expectedSimple.num(2)
        val expected2 = Matrix(
            mutableListOf(
                mutableListOf("1 ", "1 ", "1 ", "", ""),
                mutableListOf("1 ", "1 ", "1 ", "1 ", "1 "),
                mutableListOf("1 ", "1 ", "1 ", "1 ", ""),
                mutableListOf("1 ", "1 ", "", "", "")
            )
        )
        assertEquals(expected2, result)
    }

    @Test
    fun numCut() {
        val result = Matrix(
            mutableListOf(
                mutableListOf("11111", "11111", "11111", "", ""),
                mutableListOf("11111", "11111", "11111", "11111", "11111"),
                mutableListOf("1", "111111111", "11", "111", ""),
                mutableListOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).cut(3)

        val expected = Matrix(
            mutableListOf(
                mutableListOf("111", "111", "111", "", ""),
                mutableListOf("111", "111", "111", "111", "111"),
                mutableListOf("1  ", "111", "11 ", "111", ""),
                mutableListOf("111", "111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun numMakeRight() {
        val result = Matrix(
            mutableListOf(
                mutableListOf("11111", "11111", "11111", "", ""),
                mutableListOf("11111", "11111", "11111", "11111", "11111"),
                mutableListOf("1  ", "111111111", "11 ", "111", ""),
                mutableListOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).makeRight()

        val expected = Matrix(
            mutableListOf(
                mutableListOf("11111", "11111", "11111", "", ""),
                mutableListOf("11111", "11111", "11111", "11111", "11111"),
                mutableListOf("  1", "111111111", " 11", "111", ""),
                mutableListOf("111111", "111111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun numCutMakeRight() {
        val result = Matrix(
            mutableListOf(
                mutableListOf("11111", "11111", "11111", "", ""),
                mutableListOf("11111", "11111", "11111", "11111", "11111"),
                mutableListOf("1  ", "111111111", "11 ", "111", ""),
                mutableListOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).cut(3).makeRight()

        val expected = Matrix(
            mutableListOf(
                mutableListOf("111", "111", "111", "", ""),
                mutableListOf("111", "111", "111", "111", "111"),
                mutableListOf("  1", "111", " 11", "111", ""),
                mutableListOf("111", "111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }
}