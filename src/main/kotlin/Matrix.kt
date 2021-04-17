import Exceptions.MatrixException
import Exceptions.VectorException
import kotlin.math.abs
import kotlin.math.sqrt

class Vector (val values: Array<Double>) {
    val length: Int = values.size

    operator fun get(i: Int) = values[i]

    operator fun set(i: Int, value: Double) { values[i] = value }

    operator fun plus(x: Vector): Vector {
        if (x.length != length) throw VectorException("Addition vector with different length")
        return Vector(Array(length){ i -> this[i] + x[i] })
    }

    operator fun minus(x: Vector) = this + (x * (-1.0))

//    operator fun minus(x: Vector): Vector {
//        if (x.length != length) throw VectorException("Addition vector with different length")
//        return Vector(Array(length){ i -> this[i] - x[i] })
//    }

    operator fun times(a: Double) = Vector(Array(length){ i -> a * this[i] })

    fun abs() = sqrt(values.sumOf{ it*it })

    override fun toString(): String {
        var res = ""
        for (i in 0 until length) res += "${this[i]}, "
        return "(" + res.dropLast(2) + ")"
    }

    fun toMatrix() = Matrix(Array(length){ i -> Array(1) { this[i] } })
}

open class Matrix (val rows: Int,
                   val cols: Int,
                   val values: Array<Array<Double>>) {
    init {
        if (rows != values.size) throw MatrixException("Incorrect rows number in constructor")
        for (i in 0 until rows)
            if (cols != values[i].size) throw MatrixException("Incorrect columns number in constructor")
    }

    constructor(values: Array<Array<Double>>) : this(values.size, values[0].size, values){
    }

    operator fun get(i: Int, j: Int) = values[i][j]

    operator fun set(i: Int, j: Int, value: Double) {
        values[i][j] = value
    }

    operator fun plus(B: Matrix): Matrix{
        if (B.rows != rows) throw MatrixException("Incorrect rows number in addition")
        if (B.cols != cols) throw MatrixException("Incorrect columns number in addition")
        val resValues = Array(rows){ i -> Array(cols) { j ->
            this[i, j] + B[i, j]
        } }

        return Matrix(resValues)
    }

    operator fun minus(B: Matrix) = this + (B * (-1.0))

//    operator fun minus(B: Matrix): Matrix{
//        if (B.rows != rows) throw MatrixException("Incorrect rows number in addition")
//        if (B.cols != cols) throw MatrixException("Incorrect columns number in addition")
//        val resValues = Array(rows){ i -> Array(cols) { j ->
//            this[i, j] - B[i, j]
//        } }
//
//        return Matrix(resValues)
//    }

    operator fun times(B: Matrix): Matrix {
        if (B.rows != cols) throw MatrixException("Incorrect matrix sizes in multiplication")
        val resValues = Array(rows){ i -> Array(B.cols) { j ->
            var res = 0.0
            for (k in 0 until cols)
                res += this[i, k] * B[k, j]
            res
        } }

        return Matrix(resValues)
    }

    operator fun times(x: Vector): Vector {
        if (x.length != cols) throw MatrixException("Incorrect vector size in multiplication")
        val resValues: Array<Double> = Array(rows){ i ->
            var res = 0.0
            for (j in 0 until cols)
                res += this[i, j] * x[j]
            res
        }

        return Vector(resValues)
    }

    operator fun times(a: Double) = Matrix(Array(rows){ i -> Array(cols) { j -> a * this[i, j] } })

    override fun toString(): String {
        var res = ""
        for (i in 0 until rows) {
            for (j in 0 until cols)
                res += "${this[i, j]} "
            res += "\n"
        }
        res = res.dropLast(2)
        return res
    }

    fun transpose() = Matrix(Array(cols){ i -> Array(rows){ j -> this[j, i] } })

    fun toSquareMatrix() = SquareMatrix(values)
}

open class SquareMatrix(values: Array<Array<Double>>) : Matrix(values) {
    val size = rows

    init {
        if (rows != cols) throw MatrixException("Incorrect size in square matrix construction")
    }
}

class UnitMatrix(size: Int) : SquareMatrix(Array(size) { i -> Array(size) { j -> if (i == j) 1.0 else 0.0 } }) {
}



//    val A = SquareMatrix(arrayOf(arrayOf(0.7, 0.7), arrayOf(0.57, 0.89)))
//    val b = Vector(arrayOf(0.43, 0.53))