package pe.edu.upc.gamarraapp.network.requests

import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

data class SignUpRequest(
    var name: String,
    var username: String,
    var email: String,
    var role: Set<String>,
    var password: String
) : Serializable {
    constructor() : this("", "", "", HashSet<String>(Arrays.asList("user")), "")
}