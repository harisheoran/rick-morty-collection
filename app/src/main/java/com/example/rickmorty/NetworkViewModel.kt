package com.example.rickmorty

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkStateLiveData = MutableLiveData<Boolean>()

    init {
        checkNetworkState()
    }

    fun getNetworkState(): LiveData<Boolean> {
        return networkStateLiveData
    }

    private fun checkNetworkState() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        networkStateLiveData.value = isConnected
    }
}