package pe.edu.upc.gamarraapp.network.responses

import pe.edu.upc.gamarraapp.models.Clothes

class ClothesResponse(val clothes: List<Clothes>) : UserResponse() {
    constructor() : this(ArrayList<Clothes>())
}