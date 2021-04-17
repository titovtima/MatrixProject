import kotlin.math.abs
import kotlin.math.sqrt

val eps = 1e-5

fun qr_decomposition(A: SquareMatrix) : Pair<SquareMatrix, SquareMatrix> {
    var Q: SquareMatrix = UnitMatrix(A.size)
    var R = A

    for (j in 0 until R.size) {
        for (i in 0 until j) {
            if (abs(R[j, i]) >= eps) {
                val c = R[i, i] / sqrt(R[i, i] * R[i, i] + R[j, i] * R[j, i])
                val s = R[j, i] / sqrt(R[i, i] * R[i, i] + R[j, i] * R[j, i])
                val G = GivensMatrix(R.size, i, j, c, s)
                Q = multiplyGivensByMatrix(G, Q)
                R = multiplyGivensByMatrix(G, R)
//                println("$j $i")
//                println(Q)
//                println(R)
            }
        }
    }

    return Pair(Q.transpose().toSquareMatrix(), R)
}

fun main() {
//    Input data:
    val A = SquareMatrix(arrayOf(arrayOf(1.0, 2.0, 4.0), arrayOf(3.0, 3.0, 2.0), arrayOf(4.0, 1.0, 3.0)))

    val p = qr_decomposition(A)
    val Q = p.first
    val R = p.second
    println("Матрица Q:")
    println(Q)
    println("Матрица R:")
    println(R)
    println("Матрица Q*R:")
    println(Q * R)
}