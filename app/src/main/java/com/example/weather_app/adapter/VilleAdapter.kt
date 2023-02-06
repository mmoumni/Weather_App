package com.example.weather_app.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.fragment.DetailsVilleFragment
import com.example.weather_app.model.Ville


class VilleAdapter(private val villes: List<Ville>) :
    RecyclerView.Adapter<VilleAdapter.VilleViewHolder>() {

    class VilleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VilleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ville_list_item, parent, false)
        return VilleViewHolder(view)
    }


    override fun onBindViewHolder(holder: VilleViewHolder, position: Int) {
        val ville = villes[position]

        holder.itemView.findViewById<TextView>(R.id.nom).text = ville.name
        holder.itemView.setOnClickListener {
            val detailsVilleFragment = DetailsVilleFragment()
            val bundle = Bundle()
            bundle.putSerializable("ville", ville)
            detailsVilleFragment.arguments = bundle
            val fragManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            val transac = fragManager.beginTransaction()
            transac.replace(R.id.container, detailsVilleFragment)
            transac.addToBackStack(null)
            transac.commit()
        }

    }

    override fun getItemCount() = villes.size
}


