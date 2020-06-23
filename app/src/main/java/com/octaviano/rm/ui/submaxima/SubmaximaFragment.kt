package com.octaviano.rm.ui.submaxima

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.octaviano.rm.R
import com.octaviano.rm.adapter.RmAdapter
import com.octaviano.rm.ui.home.HomeFragment
import com.octaviano.rm.util.Units

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SubmaximaFragment : Fragment() {

    private lateinit var viewModelSubmaxima: ViewModelSubmaxima

    private lateinit var rmAdapter: RmAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var lblSubRm: TextView
    private lateinit var lblSubUnit: TextView

    private var sharedPref: SharedPreferences? = null
    private lateinit var unit: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModelSubmaxima = ViewModelProvider(this).get(ViewModelSubmaxima::class.java)
        val root = inflater.inflate(R.layout.fragment_second, container, false)

        initSharePrefernces()
        getUnits()
        initViews(root)
        getRM()
        configRecycleView()
        updateLabelUnits()
        hiddenFloatinActionButton()
        addObserverToRecycleVie()

        return root
    }

    private fun getUnits() {
        unit = sharedPref?.getString(HomeFragment.UNIT_KEY, Units.KG.toString()).toString()
    }

    private fun getRM() {
        val rm = arguments?.getDouble(HomeFragment.RM_KEY, 0.0)
        rm?.let {
            lblSubRm.text = "${String.format("%.2f", it)}"
            viewModelSubmaxima.calculateSubmaximas(it, unit)
        }
    }

    private fun addObserverToRecycleVie() {
        viewModelSubmaxima.rmSubmaxima.observe(viewLifecycleOwner, Observer {
            rmAdapter = RmAdapter(it)
            recyclerView.adapter = rmAdapter
        })
    }

    private fun initSharePrefernces() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

    }

    private fun updateLabelUnits() {
        sharedPref?.getString(HomeFragment.UNIT_KEY, Units.KG.toString())?.let {
            lblSubUnit.text = it
        }
    }

    private fun initViews(root: View) {
        recyclerView = root.findViewById(R.id.recycleView)
        lblSubRm = root.findViewById(R.id.lblSubRm)
        lblSubUnit = root.findViewById(R.id.lblSubUnit)
    }

    private fun configRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun hiddenFloatinActionButton() {
        (activity?.findViewById<FloatingActionButton>(R.id.fab))?.apply {
            hide()
        }
    }

}