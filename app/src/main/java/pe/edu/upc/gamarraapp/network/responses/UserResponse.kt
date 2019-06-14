package pe.edu.upc.gamarraapp.network.responses

import java.io.Serializable

abstract class UserResponse(
    val message: String
):Serializable {
    constructor() : this("")
}