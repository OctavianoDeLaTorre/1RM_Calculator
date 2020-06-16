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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.octaviano.rm.R
import com.octaviano.rm.util.Units
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var fab: FloatingActionButton
    private lateinit var lblSubmaximal: TextView

    private var sharedPref: SharedPreferences? = null
    private var isFirstInit: Boolean? = null
    private val FISRT_INIT_KEY = "FISRT_INIT_KEY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        configViews(root)
        initSharePrefernces()
        showWelcomeDialog()

        return root
    }


    private fun initSharePrefernces() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
    }

    private fun setUnit(unit: String) {
        sharedPref?.edit()?.putString(FISRT_INIT_KEY, unit)?.commit()
    }

    private fun configViews(root: View) {

        fab = activity?.findViewById(R.id.fab)!!
        fab.setOnClickListener {
            homeViewModel.calculateRM(
                lblReps.text.toString().toInt(),
                lblWeight.text.toString().toDouble()
            )
        }

        lblSubmaximal = root.findViewById(R.id.lblSubmaximal)
        lblSubmaximal.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.SecondFragment)
        }

        homeViewModel.rm.observe(viewLifecycleOwner, Observer {
            lblRM.text = String.format("%.2f", it)
        })
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
                setTitle("Bienvenido !")

                (findViewById<Button>(R.id.btnCloseWelcomeDialog)).run {
                    setOnClickListener {
                        dismiss()
                    }
                }

                (findViewById<Button>(R.id.rbKilos)).run {
                    setOnClickListener {
                        updateWeightUnitLabels(Units.KG)
                        setUnit(Units.KG.toString())
                    }
                }

                (findViewById<Button>(R.id.rbPounds)).run {
                    setOnClickListener {
                        updateWeightUnitLabels(Units.LIB)
                        setUnit(Units.LIB.toString())
                    }
                }

                show()
            }
        }
    }

    private fun updateWeightUnitLabels(unit: Units) {
        lblUnit1.text = unit.toString()
        lblUnit2.text = unit.toString()
    }

    override fun onResume() {
        super.onResume()
        fab.show()
    }

    companion object {
        const val UNIT_KEY = "UNIT"
    }
}