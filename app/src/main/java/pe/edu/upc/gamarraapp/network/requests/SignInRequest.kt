package pe.edu.upc.gamarraapp.network.requests

import java.io.Serializable

data class SignInRequest(
    val username: String,
    val password: String
) : Serializable {
    constructor() : this("", "")
}