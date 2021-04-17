fun multiplyByHouseholderMatrix(v: Vector, A: SquareMatrix) : SquareMatrix {
    return (A - (v.toMatrix() * (v.toMatrix().transpose() * A)) * 2.0).toSquareMatrix()
}

fun main() {
    val A = SquareMatrix(arrayOf(arrayOf(1.0, 2.0, 4.0), arrayOf(3.0, 3.0, 2.0), arrayOf(4.0, 1.0, 3.0)))
    val v = Vector(arrayOf(1.0, 1.0, 1.0))

    println(multiplyByHouseholderMatrix(v, A))
}