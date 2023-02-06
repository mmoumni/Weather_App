package com.example.weather_app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.adapter.VilleAdapter
import com.example.weather_app.model.Ville
import com.google.gson.Gson
import java.util.*



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VilleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class listVilleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var villeList: List<Ville>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VilleAdapter

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

        val view = inflater.inflate(R.layout.fragment_ville_list, container, false)

        val addVilleBtn = view.findViewById<Button>(R.id.addVille)
        addVilleBtn.setOnClickListener {
            val menuFragment = ajoutVilleFragment()
            val fragManager = activity?.supportFragmentManager
            val transac = fragManager?.beginTransaction()
            transac?.replace(R.id.container, menuFragment)
            transac?.addToBackStack(null)
            transac?.commit()
        }


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return view
        val gson = Gson()
        val villeJson = sharedPref.getString("villes", "")
        println(villeJson)

        villeList = if (villeJson!!.isNotEmpty()) {
            gson.fromJson(villeJson, Array<Ville>::class.java).toList()
        } else {
            listOf()
        }

        if (villeList.isEmpty()) {
            view.findViewById<TextView>(R.id.emptyMessage).visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            view.findViewById<TextView>(R.id.emptyMessage).visibility = View.GONE
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            adapter = VilleAdapter(villeList)
            recyclerView.adapter = adapter
        }




        return view
    }









    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VilleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listVilleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}