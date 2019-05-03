package pe.edu.upc.gamarraapp.network.responses

abstract class UserResponse(
    val status: String
) {
    constructor() : this("")
}