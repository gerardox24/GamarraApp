package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class User (
    val id: Int,
    var name: String,
    var password: String,
    var role: Array<String>,
    var username: String,
    var email: String
) : Serializable {
    constructor() : this(0,"","", arrayOf(),"", "")
}