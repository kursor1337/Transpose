class Matrix(given: Array<Array<String>>, transposed: Boolean = false) {

    constructor(listMatrix: List<List<String>>, transposed: Boolean = false) : this(
        listMatrix.map { row -> row.toTypedArray() }.toTypedArray(),
        transposed
    )

    constructor(string: String, transposed: Boolean = false) : this(
        string.lines().map { it.split(Regex("""\s+""")) },
        transposed
    )

    val xsize: Int = given.maxOf { row -> row.size }
    val ysize: Int = given.size
    val matrix: Array<Array<String>>

    init {
        val xsize = if (transposed) given.size
        else given.maxOf { row -> row.size }

        val ysize = if (transposed) given.maxOf { row -> row.size }
        else given.size

        matrix = if (transposed) {
            Array(ysize) { i ->
                Array(xsize) { j ->
                    given[j].getOrElse(i) { "" }
                }
            }
        } else Array(ysize) { i ->
            Array(xsize) { j ->
                given[i].getOrElse(j) { "" }
            }
        }
    }

    operator fun get(i: Int, j: Int) = matrix[i][j]
    operator fun set(i: Int, j: Int, value: String) {
        matrix[i][j] = value
    }

    fun transposed(): Matrix {
        val transposedArray = Array(xsize) { i -> Array(ysize) { j -> matrix[j][i] } }
        return Matrix(transposedArray)
    }

    fun num(n: Int): Matrix {
        this.mapToThis { string ->
            if (string != "" && string.length < n) {
                string + " ".repeat(n - string.length)
            } else string
        }
        return this
    }

    fun cut(n: Int): Matrix {
        this.mapToThis { string ->
            if (string != "" && string.length > n) {
                string.substring(0, n)
            } else string
        }
        return this
    }

    fun makeRight(): Matrix {
        this.mapToThis { string ->
            val sep = string.indexOfFirst { it == ' ' }
            if (sep != -1) {
                val spaces = string.substring(sep)
                val word = string.substring(0, sep)
                spaces + word
            } else string
        }
        return this
    }

    fun applyArgs(num: Int? = 10, cut: Boolean, byRightSide: Boolean): Matrix {
        if (num == null && !cut && !byRightSide) return this
        val n = num ?: 10
        num(n)
        if (cut) cut(n)
        if (byRightSide) makeRight()
        return this
    }

//    fun isLastOnRow(y: Int, x: Int): Boolean {
//        if (x == xsize - 1) return true
//        for (j in x + 1 until xsize) {
//            if (matrix[y][j] != "") return false
//        }
//        return true
//    }
//
//    fun isLastOnColumn(y: Int, x: Int): Boolean {
//        if (y == ysize - 1) return true
//        for (i in y + 1 until ysize) {
//            if (matrix[i][x] != "") return false
//        }
//        return true
//    }

    override fun toString(): String {
        val sb = StringBuilder()

        matrix.forEachIndexed { i, row ->
            row.forEachIndexed { j, string ->
                if (string != "") sb.append("$string ")
            }
            sb.deleteCharAt(sb.lastIndex)
            if (i < matrix.size - 1) sb.append("\n")
        }
        return sb.toString()
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


    inline fun Matrix.mapToThis(action: (String) -> String) {
        forEachIndexed { i, j, string ->
            this[i, j] = action(string)
        }
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
}