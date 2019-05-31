package pe.edu.upc.gamarraapp.controllers.fragments

import androidx.fragment.app.Fragment
import pe.edu.upc.gamarraapp.adapters.ClotheAdapter
import pe.edu.upc.gamarraapp.network.GamarraApi


class ItemFragment : Fragment(){
    lateinit var clotheAdapter: ClotheAdapter
    lateinit var service: GamarraApi

    val TAG_LOGS = "detailClothe"


}