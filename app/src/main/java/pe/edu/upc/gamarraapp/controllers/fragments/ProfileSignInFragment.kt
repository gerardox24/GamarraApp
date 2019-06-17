package pe.edu.upc.gamarraapp.controllers.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_profile_sign_in.*

import pe.edu.upc.gamarraapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileSignInFragment(var supportFragmentManager: FragmentManager) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("gamarra-app-shared-preferences", Context.MODE_PRIVATE) ?: return
        val accessTokenDefault = ""
        val idDefault = 0
        logoutButton.setOnClickListener {

            with (sharedPref.edit()) {
                putString("accessToken", accessTokenDefault)
                commit()

                id?.apply {
                    putInt("id", idDefault)
                }
                commit()
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, ProfileFragment(supportFragmentManager))
                .commit() > 0
        }
    }
}
