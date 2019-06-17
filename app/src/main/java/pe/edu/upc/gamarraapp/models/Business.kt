package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Business(
    var id: Int,
    var name: String?,
    var urllogo: String?,
    var userId: User?
) : Serializable {
    constructor(): this(0, "", "", User())
}