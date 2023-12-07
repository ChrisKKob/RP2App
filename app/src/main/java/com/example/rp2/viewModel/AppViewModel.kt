package com.example.rp2.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel: ViewModel() {
    var texto by mutableStateOf("")
    var capturedImageUri by mutableStateOf<Uri>(Uri.EMPTY)

    fun updateTexto(texto: String){
        this.texto = texto
    }

    fun updateUri(uri: Uri){
        capturedImageUri = uri
    }

}