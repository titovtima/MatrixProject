fun qr_decomposition_by_Householder(A: SquareMatrix) : Pair<SquareMatrix, SquareMatrix> {
    var Q: SquareMatrix = UnitMatrix(A.size)
    var R = A

    for (i in 0 until A.size-1) {
        val v = Vector(Array(A.size){ j -> if (j < i) 0.0 else R[j, i] })
        val u = v * (1.0 / v.abs())
        val u_minus_e = u - Vector(Array(A.size){ j -> if (j == i) 1.0 else 0.0 })
        val v_to_rotate = u_minus_e * (1.0 / u_minus_e.abs())

        Q = multiplyByHouseholderMatrix(v_to_rotate, Q)
        R = multiplyByHouseholderMatrix(v_to_rotate, R)
    }

    return Pair(Q.transpose().toSquareMatrix(), R)
}

fun main() {
//    Input data:
    val A = SquareMatrix(arrayOf(arrayOf(1.0, 2.0, 4.0), arrayOf(3.0, 3.0, 2.0), arrayOf(4.0, 1.0, 3.0)))

    val p = qr_decomposition_by_Householder(SquareMatrix(Array(A.size){ i -> Array(A.size){ j -> A[i, j] } }))
    val Q = p.first
    val R = p.second
    println("Матрица Q:")
    println(Q)
    println("Матрица R:")
    println(R)
    println("Матрица Q*R:")
    println(Q * R)
    println("Матрица A:")
    println(A)
}