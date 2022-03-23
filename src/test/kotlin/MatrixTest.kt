import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixTest {


    val expected = Matrix(
        listOf(
            listOf("1", "1", "1", null, null),
            listOf("1", "1", "1", "1", "1"),
            listOf("1", "1", "1", "1", null),
            listOf("1", "1", null, null, null)
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

        assertEquals(expected, result)
    }

    @Test
    fun fromString() {
        val result = Matrix.fromString("""
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent())

        assertEquals(expected, result)
    }

    @Test
    fun toStringTest() {
        assertEquals("""
            1 1 1
            1 1 1 1 1
            1 1 1 1
            1 1
        """.trimIndent(), expected.toString())
    }

    @Test
    fun fromToString() {
        assertEquals(expected, Matrix.fromString(expected.toString()))
    }

    @Test
    fun toFromString() {
        assertEquals(expected.toString(), Matrix.fromString(expected.toString()).toString())
    }

    @Test
    fun transposed() {
        val result = Matrix(
            listOf(
                listOf("1", "1", "1", "1"),
                listOf("1", "1", "1", "1"),
                listOf("1", "1", "1", null),
                listOf(null, "1", "1", null),
                listOf(null, "1", null, null)
            )
        )
        assertEquals(expected, result.transposed())
    }



}