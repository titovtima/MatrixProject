import kotlin.math.*

class GivensMatrix(size: Int, i: Int, j: Int, val phi: Double) : SquareMatrix(Array(size){ i1 -> Array(size){ j1 ->
    if (((i1 != i) && (i1 != j)) || ((j1 != i) && (j1 != j)))
        if (i1 == j1) 1.0 else 0.0
    else
        if (i1 == j1) cos(phi)
        else if (i1 < j1) sin(phi) else -sin(phi)
} }) {
    val i: Int
    val j: Int
    val c = cos(phi)
    val s = sin(phi)

    init {
        if (i < j) {
            this.i = i
            this.j = j
        } else {
            this.i = j
            this.j = i
        }
    }

    constructor(size: Int, i: Int, j: Int, c: Double, s: Double) : this(size, i, j,
        if (abs(sin(acos(c)) - s) < 1e-3) acos(c) else 2 * PI - acos(c))
}