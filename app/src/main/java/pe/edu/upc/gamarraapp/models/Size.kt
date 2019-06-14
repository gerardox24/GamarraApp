package pe.edu.upc.gamarraapp.models

import java.io.Serializable

data class Size (
    val id : Int,
    val name : String
) : Serializable {
    constructor() : this ( 0 , "")
}