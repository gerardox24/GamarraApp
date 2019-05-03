package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Clothe (
    val id : Int,
    val name : String,
    val description: String,
    val urlphoto: String,
    val size_id: Int,
    val cathegory_id: Int
) : Serializable {
    constructor() : this(0,"","","",0,0)
}