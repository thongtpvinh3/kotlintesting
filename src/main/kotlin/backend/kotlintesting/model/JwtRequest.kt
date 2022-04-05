package backend.kotlintesting.model

@kotlinx.serialization.Serializable
data class JwtRequest(
    private val username: String,
    private val password: String
)
