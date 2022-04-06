import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixTest {


    val expectedSimple = Matrix(
        listOf(
            listOf("1", "1", "1", "", ""),
            listOf("1", "1", "1", "1", "1"),
            listOf("1", "1", "1", "1", ""),
            listOf("1", "1", "", "", "")
        )
    )

    @Test
    fun constructor() {
        val result = Matrix(
            listOf(
                listOf("1", "1", "1"),
                listOf("1", "1", "1", "1", "1"),
                listOf("1", "1", "1", "1"),
                listOf("1", "1")
            )
        )

        assertEquals(expectedSimple, result)
    }

    @Test
    fun fromString() {
        val result = Matrix("""
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent())

        assertEquals(expectedSimple, result)
    }

    @Test
    fun toStringTest() {
        assertEquals("""
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent(), expectedSimple.toString())
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
    fun transposed() {
        val result = Matrix(
            listOf(
                listOf("1", "1", "1", "1"),
                listOf("1", "1", "1", "1"),
                listOf("1", "1", "1", ""),
                listOf("", "1", "1", ""),
                listOf("", "1", "", "")
            )
        )
        assertEquals(expectedSimple, result.transposed())
    }

    @Test
    fun num() {
        val result = expectedSimple.num(2)
        val expected2 = Matrix(
            listOf(
                listOf("1 ", "1 ", "1 ", "", ""),
                listOf("1 ", "1 ", "1 ", "1 ", "1 "),
                listOf("1 ", "1 ", "1 ", "1 ", ""),
                listOf("1 ", "1 ", "", "", "")
            )
        )
        assertEquals(expected2, result)
    }

    @Test
    fun numCut() {
        val result = Matrix(
            listOf(
                listOf("11111", "11111", "11111", "", ""),
                listOf("11111", "11111", "11111", "11111", "11111"),
                listOf("1", "111111111", "11", "111", ""),
                listOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).cut(3)

        val expected = Matrix(
            listOf(
                listOf("111", "111", "111", "", ""),
                listOf("111", "111", "111", "111", "111"),
                listOf("1  ", "111", "11 ", "111", ""),
                listOf("111", "111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun numMakeRight() {
        val result = Matrix(
            listOf(
                listOf("11111", "11111", "11111", "", ""),
                listOf("11111", "11111", "11111", "11111", "11111"),
                listOf("1  ", "111111111", "11 ", "111", ""),
                listOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).makeRight()

        val expected = Matrix(
            listOf(
                listOf("11111", "11111", "11111", "", ""),
                listOf("11111", "11111", "11111", "11111", "11111"),
                listOf("  1", "111111111", " 11", "111", ""),
                listOf("111111", "111111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun numCutMakeRight() {
        val result = Matrix(
            listOf(
                listOf("11111", "11111", "11111", "", ""),
                listOf("11111", "11111", "11111", "11111", "11111"),
                listOf("1  ", "111111111", "11 ", "111", ""),
                listOf("111111", "111111", "", "", "")
            )
        )

        result.num(3).cut(3).makeRight()

        val expected = Matrix(
            listOf(
                listOf("111", "111", "111", "", ""),
                listOf("111", "111", "111", "111", "111"),
                listOf("  1", "111", " 11", "111", ""),
                listOf("111", "111", "", "", "")
            )
        )
        assertEquals(expected, result)
    }
}