package pe.edu.upc.gamarraapp.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.util.*

data class User (
    var id: Int,
    var firstName: String,
    var secondName: String,
    var fathersLastName: String,
    var mothersLastName: String,
    var dni: String,
    var password: String,
    var email: String,
    var gender: Boolean
    // TODO El servicio tiene problemas al deserealizar la fecha, produce un error 400
    // Usé @JsonFormat pero no se reconoció
    //var birthdate: Date
) : Serializable {

    constructor() : this(0,"", "","","","","","",true)
}