package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Business(
    val id: Int,
    val name: String?,
    val urllogo: String?
) : Serializable {
    constructor(): this(0, "", "")
}