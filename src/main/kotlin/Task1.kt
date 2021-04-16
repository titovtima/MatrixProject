import kotlin.math.abs
import kotlin.math.sqrt

data class Circle (val x: Double, val y: Double, val r: Double)

fun SquareMatrix.findGershgorinCircles(): Array<Circle> =
    Array(size) { i ->
        var r = 0.0
        for (j in 0 until size)
            r += abs(this[i, j])
        Circle(this[i, i], 0.0, r)
    }

fun Circle.isInsideUnitCircle() = sqrt(x*x + y*y) + r < 1

var circlesAreGood = false

fun iterationMethod(A: SquareMatrix, b: Vector, x_0: Vector, eps: Double): Vector? {
    var badIterations = 0
    var x = x_0
    while ((x - A * x - b).abs() >= eps) {
        val old_x = x
        x = A * x + b
        if (x.abs() >= old_x.abs() + 1)
            badIterations++
        else
            badIterations = 0
        if (badIterations >= 20 && !circlesAreGood)
            return null
    }
    return x
}

fun main() {
//    Input values:
    val eps = 0.000001
    val A = SquareMatrix(arrayOf(arrayOf(0.5, -0.6), arrayOf(0.1, 0.98)))
    val b = Vector(arrayOf(0.4, -0.01))
    val x_0 = Vector(arrayOf(1.0, 1.0)) // Put your approximate solution here

    circlesAreGood = A.findGershgorinCircles().all { it.isInsideUnitCircle() }
    val res = iterationMethod(A, b, x_0, eps)
    if (res == null)
        println(0)
    else
        println(res.toString())
}
