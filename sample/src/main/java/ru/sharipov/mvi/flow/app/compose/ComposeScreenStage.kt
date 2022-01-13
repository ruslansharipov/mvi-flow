package ru.sharipov.mvi.flow.app.compose

/**
 * @param code this param is here just to simplify the code of stage switching
 */
enum class ComposeScreenStage(val code: Int) {
    INITIAL(0),
    MIDDLE(1),
    FINAL(2);

    companion object {
        fun getByCode(code: Int) : ComposeScreenStage {
            return when(code) {
                0 -> INITIAL
                1 -> MIDDLE
                else -> FINAL
            }
        }
    }
}