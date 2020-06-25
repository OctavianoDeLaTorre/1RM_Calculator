package com.octaviano.rm.ui

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.octaviano.rm.R
import com.octaviano.rm.ui.home.HomeFragment
import com.octaviano.rm.util.Units

class MainActivity : AppCompatActivity() {

    private var sharedPref: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initSharePrefernces()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                createSettingsDialog()
                true
            }

            R.id.action_about -> {
                createAboutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createAboutDialog() {
        Dialog(this).run {
            setContentView(R.layout.about_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            (findViewById<Button>(R.id.btnCloseAboutDialog)).run {
                setOnClickListener {
                    dismiss()
                }
            }
            show()
        }
    }

    private fun initSharePrefernces() {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
    }

    private fun createSettingsDialog() {
        Dialog(this).run {
            setContentView(R.layout.settings_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            (findViewById<Button>(R.id.btnCloseWelcomeDialog)).run {
                setOnClickListener {
                    dismiss()
                }
            }

            (findViewById<Button>(R.id.rbKg)).run {
                setOnClickListener {
                    setUnit(Units.KG.toString())
                }
            }

            (findViewById<Button>(R.id.rbLib)).run {
                setOnClickListener {
                    setUnit(Units.LIB.toString())
                }
            }

            show()
        }
    }

    private fun setUnit(unit: String) {
        sharedPref?.edit()?.putString(HomeFragment.UNIT_KEY, unit)?.commit()
        fragmentTransaction(HomeFragment())
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

}