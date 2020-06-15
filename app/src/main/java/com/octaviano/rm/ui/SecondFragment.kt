package com.octaviano.rm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.octaviano.rm.R
import com.octaviano.rm.adapter.RmAdapter
import com.octaviano.rm.model.RmPorcentage

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var rmAdapter: RmAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_second, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val list = ArrayList<RmPorcentage>()

        list.add(RmPorcentage("100 %", "100 kg"))
        list.add(RmPorcentage("90 %", "90 kg"))

        rmAdapter = RmAdapter(list)

        recyclerView.adapter = rmAdapter

        configViews(root)

        return root

    }

    private fun configViews(root: View?) {
        val fab = (activity?.findViewById<FloatingActionButton>(R.id.fab))?.apply {
            hide()
        }
    }

}