package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class LogInRequest(
    val username: String,
    val password: String
) : Serializable {
    constructor() : this("", "")
}