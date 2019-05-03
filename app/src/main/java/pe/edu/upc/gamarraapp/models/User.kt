package pe.edu.upc.gamarraapp.models

import java.io.Serializable
import java.util.*

data class User (
    val id: Int,
    val firstName: String,
    val secondName: String,
    val fathersLastName: String,
    val mothersLastName: String,
    val dni: String,
    val password: String,
    val email: String,
    val gender: Boolean,
    val birthdate: Date
) : Serializable {
    constructor() : this(0,"","","","","","","",true,Date())
}