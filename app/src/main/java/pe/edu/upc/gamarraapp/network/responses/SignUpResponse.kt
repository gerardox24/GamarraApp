package pe.edu.upc.gamarraapp.network.responses

import java.io.Serializable

data class SignUpResponse(
    val message: String
) : Serializable {
    constructor() : this("")
}