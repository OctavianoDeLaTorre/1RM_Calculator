package com.octaviano.rm

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
import androidx.fragment.app.Fragment
import com.octaviano.rm.util.Units
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {


    private var sharedPref: SharedPreferences? = null
    private var isFirstInit: Boolean? = null
    private val FISRT_INIT_KEY = "FISRT_INIT_KEY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        showWelcomeDialog()

        return root
    }

    private fun showWelcomeDialog() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        isFirstInit = sharedPref?.getBoolean(FISRT_INIT_KEY, true)

        isFirstInit?.let {
            when (it) {
                true -> {
                    createWelcomeDialog()

                    sharedPref?.edit()?.putBoolean(FISRT_INIT_KEY, true)?.apply()
                }

                false -> return
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
                    }
                }

                (findViewById<Button>(R.id.rbPounds)).run {
                    setOnClickListener {
                        updateWeightUnitLabels(Units.LIB)
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

}