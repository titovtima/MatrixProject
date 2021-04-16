fun generateLowerAndUpper(A: SquareMatrix) : Pair<SquareMatrix, SquareMatrix> {
    val L = SquareMatrix(Array(A.size){ Array(A.size){ 0.0 } })
    val U = SquareMatrix(Array(A.size){ Array(A.size){ 0.0 } })
    for (i in 0 until A.size)
        for (j in 0 until A.size)
            if (i < j)
                U[i, j] = A[i,j]
            else
                L[i, j] = A[i, j]
    return Pair(L, U)
}

fun GaussSeidelMethod(A: SquareMatrix, b: Vector, x_0: Vector, eps: Double): Vector? {
    val p = generateLowerAndUpper(A)
    val L = p.first
    val neg_U = p.second * (-1.0)

    var badIterations = 0
    var x = x_0

    while ((A * x - b).abs() >= eps) {
        val old_x = x
//        println(x)

        val rightPart = neg_U * x + b
        x = Vector(Array(x.length){ 0.0 })

        for (i in 0 until x.length) {
            var prevRes = 0.0
            if (L[i, i] == 0.0) {
                x[i] = 0.0
                continue
            }
            for (j in 0 until i)
                prevRes += x[j] * L[i, j]
            x[i] = (rightPart[i] - prevRes) / L[i, i]
        }

        if (x.abs() - old_x.abs() >= 1)
            badIterations++
        else
            badIterations = 0

        if (badIterations >= 20)
            return null
    }

    return x
}

fun main() {
//    Input values:
    val eps = 0.000001
//    val A = SquareMatrix(arrayOf(arrayOf(0.5, 0.6), arrayOf(-0.1, 0.02)))
//    val b = Vector(arrayOf(0.4, -0.01))
    val A = SquareMatrix(arrayOf(arrayOf(0.7, 0.7), arrayOf(0.57, 0.89)))
    val b = Vector(arrayOf(0.43, 0.53))
    val x_0 = Vector(arrayOf(1.0, 1.0)) // Put your approximate solution here

    var zerosOnDiag = true
    for (i in 0 until A.size)
        if (A[i, i] != 0.0)
            zerosOnDiag = false
    if (zerosOnDiag) {
        println(0)
        return
    }

    val res = GaussSeidelMethod(A, b, x_0, eps)
    if (res == null)
        println(0)
    else
        println(res.toString())
}