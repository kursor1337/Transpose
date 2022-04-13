class Matrix(given: MutableList<MutableList<String>>, transposed: Boolean = false) {

    constructor(string: String, transposed: Boolean = false) : this(
        string.lines().map { it.split(Regex("""\s+""")).toMutableList() }.toMutableList() ,
        transposed
    )

    val xsize: Int
    val ysize: Int
    val matrix: Array<Array<String>>

    init {
        xsize = if (transposed) given.size
        else given.maxOf { row -> row.size }

        ysize = if (transposed) given.maxOf { row -> row.size }
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

    inline fun forEachIndexed(action: (Int, Int, String) -> Unit) {
        for (y in 0 until ysize) {
            for (x in 0 until xsize) {
                action(y, x, this[y, x])
            }
        }
    }

    inline fun forEach(action: (String) -> Unit) {
        forEachIndexed { x, y, value -> action(value) }
    }


    inline fun mapToThis(action: (String) -> String) {
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