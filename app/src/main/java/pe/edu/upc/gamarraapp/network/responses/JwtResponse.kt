package pe.edu.upc.gamarraapp.network.responses

import java.io.Serializable

data class JwtResponse(
    val username: String?,
    val id: Int?,
    val accessToken: String?
) : Serializable {
    constructor() : this("", 0, "")
}