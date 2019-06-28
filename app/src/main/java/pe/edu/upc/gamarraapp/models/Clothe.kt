package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Clothe (
    val id : Int,
    val name : String?,
    val description: String?,
    val urlphoto: String?,
    val sizeId: Size?,
    val categoryId: Category?
) : Serializable {
    constructor() : this(0,"","","", Size(),Category())
}