package backend.kotlintesting.model

@kotlinx.serialization.Serializable
data class ForAddMoreQuestion (
    var idQuestion: MutableSet<Int> = mutableSetOf()
)