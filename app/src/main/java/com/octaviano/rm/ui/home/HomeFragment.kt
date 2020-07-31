package com.octaviano.rm.ui.home

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.octaviano.rm.R
import com.octaviano.rm.util.Units
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var fab: FloatingActionButton
    private lateinit var lblSubmaximal: TextView

    private var sharedPref: SharedPreferences? = null
    private var isFirstInit: Boolean? = null
    private lateinit var lblUnit1: TextView
    private lateinit var lblUnit2: TextView

    private var rm = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initSharePrefernces()
        configViews(root)
        showWelcomeDialog()

        return root
    }

    private fun initSharePrefernces() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    }

    private fun setUnit(unit: String) {
        sharedPref?.edit()?.putString(UNIT_KEY, unit)?.commit()
    }

    private fun getUnits(): String? {
        return sharedPref?.getString(UNIT_KEY, Units.KG.toString())
    }

    private fun configViews(root: View) {

        fab = activity?.findViewById(R.id.fab)!!
        fab.setOnClickListener {
            val reps = lblReps.text.toString()
            val weight = lblWeight.text.toString()
            if (reps.isEmpty() || weight.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.debes_ingresar_reps),
                    Toast.LENGTH_LONG
                ).show()
            } else if (reps.toInt() == 0) {
                Toast.makeText(
                    context,
                    getString(R.string.debes_ingresar_reps),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                homeViewModel.calculateRM(
                    lblReps.text.toString().toInt(),
                    lblWeight.text.toString().toDouble()
                )
            }
        }

        lblSubmaximal = root.findViewById(R.id.lblSubmaximal)
        lblSubmaximal.setOnClickListener {
            if (rm > 0) {
                val bundle = Bundle()
                bundle.putDouble(RM_KEY, rm)
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.SecondFragment, bundle)
            } else {
                Toast.makeText(context, getString(R.string.debes_calcular_rm), Toast.LENGTH_LONG)
                    .show()
            }
        }

        homeViewModel.rm.observe(viewLifecycleOwner, Observer {
            rm = it
            lblRM.text = String.format("%.2f", it)
        })

        lblUnit1 = root.findViewById(R.id.lblUnit1)
        lblUnit2 = root.findViewById(R.id.lblUnit2)

        getUnits()?.let {
            updateWeightUnitLabels(it)
        }
    }

    private fun showWelcomeDialog() {
        sharedPref?.getBoolean(FISRT_INIT_KEY, true)?.let {
            if (it) {
                createWelcomeDialog()
                sharedPref?.edit()?.putBoolean(FISRT_INIT_KEY, false)?.commit()
            }
        }

    }

    private fun createWelcomeDialog() {
        context?.let {
            Dialog(it).run {
                setContentView(R.layout.welcome_dialog)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                (findViewById<Button>(R.id.btnCloseWelcomeDialog)).run {
                    setOnClickListener {
                        dismiss()
                    }
                }

                (findViewById<Button>(R.id.rbKilos)).run {
                    setOnClickListener {
                        updateWeightUnitLabels(Units.KG.toString())
                        setUnit(Units.KG.toString())
                    }
                }

                (findViewById<Button>(R.id.rbPounds)).run {
                    setOnClickListener {
                        updateWeightUnitLabels(Units.LIB.toString())
                        setUnit(Units.LIB.toString())
                    }
                }

                show()
            }
        }
    }

    private fun updateWeightUnitLabels(unit: String) {
        lblUnit1.text = unit
        lblUnit2.text = unit
    }

    override fun onResume() {
        super.onResume()
        fab.show()
    }

    companion object {
        const val UNIT_KEY = "UNIT"
        const val RM_KEY = "RM_KEY"
        private val FISRT_INIT_KEY = "FISRT_INIT_KEY"
    }
}