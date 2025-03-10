package com.example.pocketmoster.ui.secret

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SecretViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Próximamente"
    }
    val text: LiveData<String> = _text
}