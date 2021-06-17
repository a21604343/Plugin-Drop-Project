package data

data class JUnitSummary(
    val numTests: Int,
    val numFailures: Int,
    val numErrors: Int,
    val numSkipped: Int,
    val ellapsed: Float
) {

    val progress: Int
        get() {
            return numTests - (numErrors + numFailures)
        }

    fun toStr(): String {
        return "${progress}/${numTests}"
    }
}