package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Clothes (
    val id : Int,
    val name : String,
    val description: String,
    val urlphoto: String,
    val size_id: Int,
    val category_id: Int
) : Serializable {
    constructor() : this(0,"","","",0,0)
}