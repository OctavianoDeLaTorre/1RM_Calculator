package com.octaviano.rm.ui.submaxima

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.octaviano.rm.model.RmPorcentage

class ViewModelSubmaxima : ViewModel() {
    private val _rmSubmaxima = MutableLiveData<ArrayList<RmPorcentage>>()
    val rmSubmaxima: LiveData<ArrayList<RmPorcentage>> = _rmSubmaxima

    fun calculateSubmaximas(rm: Double, unit: String) {
        val listRM = ArrayList<RmPorcentage>()
        var aux = 1.25

        while (aux >= 0.05) {
            listRM.add(
                RmPorcentage(
                    "${String.format("%.0f", (aux * 100))} %",
                    "${String.format("%.2f", (rm * aux))} $unit"
                )
            )
            aux -= 0.05
        }
        _rmSubmaxima.value = listRM
    }
}