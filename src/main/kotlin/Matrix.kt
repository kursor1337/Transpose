class Matrix(given: Array<Array<String>>) {

    constructor(listMatrix: List<List<String>>): this(listMatrix.map { row -> row.toTypedArray() }.toTypedArray())

    val xsize = given.maxOf { row -> row.size }
    val ysize = given.size
    val matrix = Array(ysize) { i ->
        Array(xsize) { j ->
            if (j < given[i].size) given[i][j]
            else ""
        }
    }

    operator fun get(i: Int, j: Int) = matrix[i][j]
    operator fun set(i: Int, j: Int, value: String) {
        matrix[i][j] = value
    }

    fun transposed(): Matrix {
        val transposedArray = Array(xsize) {i -> Array(ysize) { j -> matrix[j][i] } }
        return Matrix(transposedArray)
    }

    fun num(n: Int): Matrix {
        this.forEachIndexed { i, j, string ->
            if (string != "" && string.length < n) {
                this[i, j] = string + " ".repeat(n - string.length)
            }
        }

        return this
    }

    fun cut(n: Int): Matrix {
        this.forEachIndexed { i, j, string ->
            if (string != "" && string.length > n) {
                this[i, j] = string.substring(0, n)
            }
        }
        return this
    }

    fun makeRight(): Matrix {
        this.forEachIndexed { i, j, string ->
            val sep = string.indexOf(" ")
            if (sep != -1) {
                val spaces = string.substring(sep)
                val word = string.substring(0, sep)
                this[i, j] = spaces + word
            }
        }
        return this
    }

    fun isLastOnRow(y: Int, x: Int): Boolean {
        if (x == xsize - 1) return true
        for (j in x + 1 until xsize) {
            if (matrix[y][j] != "") return false
        }
        return true
    }

    fun isLastOnColumn(y: Int, x: Int): Boolean {
        if (y == ysize - 1) return true
        for (i in y + 1 until ysize) {
            if (matrix[i][x] != "") return false
        }
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, string ->
                if (string != "") {
                    if (!isLastOnRow(i, j)) {
                        sb.append("$string ")
                    } else sb.append(string)
                }
            }
            if (i < matrix.size - 1) sb.append("\n")

        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (xsize != other.xsize) return false
        if (ysize != other.ysize) return false
        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xsize
        result = 31 * result + ysize
        result = 31 * result + matrix.contentDeepHashCode()
        return result
    }

    inline fun Matrix.forEachIndexed(action: (Int, Int, String) -> Unit) {
        for (y in 0 until ysize) {
            for (x in 0 until xsize) {
                action(y, x, this[y, x])
            }
        }
    }

    inline fun Matrix.forEach(action: (String) -> Unit) {
        forEachIndexed { x, y, value -> action(value) }
    }


    companion object {
        fun fromString(string: String): Matrix {
            val listOfLists = mutableListOf<List<String>>()
            val splitRegex = Regex("""\s+""")
            for (line in string.lines()) listOfLists.add(line.split(splitRegex))
            return Matrix(listOfLists)
        }
    }
}