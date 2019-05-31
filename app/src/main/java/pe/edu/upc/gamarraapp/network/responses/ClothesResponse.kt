package pe.edu.upc.gamarraapp.network.responses

import pe.edu.upc.gamarraapp.models.Clothe

class ClothesResponse(val clothes: List<Clothe>) : UserResponse() {
    constructor() : this(ArrayList<Clothe>())
}