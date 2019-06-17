package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Shop(
    val address: String?,
    val businessId: Business?,
    val directions: String?,
    val galleryId: Gallery?,
    val id: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val urlphoto: String?
) : Serializable {
    constructor() : this("", Business(), "", Gallery(), 0, 0.0, 0.0, "")
}