package com.octaviano.rm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _rm = MutableLiveData<Double>()
    val rm: LiveData<Double> = _rm

    fun calculateRM(reps: Int, weight: Double) {
        _rm.value = 100 * weight / (102.78 - (2.78 * reps))
    }
}