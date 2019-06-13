package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Gallery(
    val address: String?,
    val id: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?
) : Serializable {
    constructor() : this("", 0, 0.0,0.0,"")
}