package com.octaviano.rm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.octaviano.rm.R
import com.octaviano.rm.model.RmPorcentage

class RmAdapter(private var rmValues: List<RmPorcentage>) :
    RecyclerView.Adapter<RmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rm_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rmValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rmValues.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var lblRmPorcengate: TextView
        var lblPorcentageWeight: TextView

        init {
            lblRmPorcengate = itemView.findViewById(R.id.lblRmPorcengate)
            lblPorcentageWeight = itemView.findViewById(R.id.lblPorcentageWeight)
        }

        fun bind(rmPorcentage: RmPorcentage) {
            lblRmPorcengate.text = rmPorcentage.rmPorcentage
            lblPorcentageWeight.text = rmPorcentage.porcentageWeight
        }
    }
}