package com.example.rp2

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.FileNotFoundException
import java.io.IOException

class TextRecognizer {
    // When using Latin script library
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun getResultText(context : Context, uri : Uri) : Task<Text>{
        val image = setImage(context = context, uri = uri)
        return recognizer.process(image)
    }

     //funcao q retorna um objeto do tipo InputImage dado um contexto e URI da
     //foto tirada pela câmera, que posteriormente será processado pela IA
     //para reconhecimento de texto
    private fun setImage(context : Context, uri : Uri) : InputImage{
        var image: InputImage = InputImage.fromFilePath(context, uri)

        return image
    }

}