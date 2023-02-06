package com.example.weather_app.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather_app.R
import com.example.weather_app.model.Ville
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ajoutVilleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class ajoutVilleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var villeList: List<Ville>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view= inflater.inflate(R.layout.fragment_ajout_ville, container, false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return view
        val gson = Gson()
        val villeJson = sharedPref.getString("villes", null)
        if (villeJson != null) {
            villeList = gson.fromJson(villeJson, Array<Ville>::class.java).toList()
        } else {
            villeList = ArrayList()
        }

        val nameEditText = view.findViewById<EditText>(R.id.nom)
        val addVilleButton = view.findViewById<Button>(R.id.btn_add_ville)

            addVilleButton.setOnClickListener {
            val nom = nameEditText.text.toString()
            if (villeList.any { it.name == nom }) {
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.ville) + ' ' + nom + ' ' + context?.getString(R.string.existe_déjà),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            villeList = villeList + Ville(nom)

            saveVilles(villeList)

            val menuFragment = listVilleFragment()
            val fragManager = activity?.supportFragmentManager
            val villeTransaction = fragManager?.beginTransaction()
            villeTransaction?.replace(R.id.container, menuFragment)
            villeTransaction?.addToBackStack(null)
            villeTransaction?.commit()

            val successMessage = context?.getString(R.string.ville) + ' ' + nom + ' ' + context?.getString(R.string.ajoute_avec_succes)
            Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show()
        }


        val Button_cancel = view.findViewById<Button>(R.id.btn_cancel)
        Button_cancel.setOnClickListener {
            val menuFragment = listVilleFragment()
            val fragManager = activity?.supportFragmentManager
            val villeTransaction = fragManager?.beginTransaction()
            villeTransaction?.replace(R.id.container, menuFragment)
            villeTransaction?.addToBackStack(null)
            villeTransaction?.commit()
        }



        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    addVilleButton.isEnabled = true
                } else {
                    addVilleButton.isEnabled = false
                }
            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })



        return view

    }
    fun saveVilles(villes: List<Ville>) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val editor = sharedPref.edit()
        val gson = Gson()
        val villeJson = gson.toJson(villes)
        editor.putString("villes", villeJson)
        editor.apply()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ajoutVilleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ajoutVilleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}