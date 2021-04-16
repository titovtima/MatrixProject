import Exceptions.MatrixException
import kotlin.math.cos
import kotlin.math.sin

fun multiplyGivensByMatrix(G: GivensMatrix, A: SquareMatrix): SquareMatrix {
    if (G.size != A.size) throw MatrixException("Incorrect size in multiplication by Givens matrix")

    val resValues = Array(A.size){ i -> Array(A.size){ j ->
        if (i != G.i && i != G.j)
            A[i, j]
        else
            when {
                i == G.i -> G.c * A[G.i, j] + G.s * A[G.j, j]
                else -> -G.s * A[G.i, j] + G.c * A[G.j, j]
            }
    } }

    return SquareMatrix(resValues)
}

fun main() {
//    Input data (example from wikipedia)
    val A = SquareMatrix(arrayOf(arrayOf(6.0, 5.0, 0.0), arrayOf(5.0, 1.0, 4.0), arrayOf(0.0, 4.0, 3.0)))
    val G = GivensMatrix(3, 0, 1, 0.7682, 0.6402)

    println(multiplyGivensByMatrix(G, A)) //fast multiplication in O(n) operations
    println()
    println(G * A) //usual multiplication just as matrices in O(n^3) operations
}