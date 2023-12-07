package com.example.rp2.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rp2.BuildConfig
import com.example.rp2.R
import com.example.rp2.TextRecognizer
import com.example.rp2.ui.theme.RP2Theme
import com.example.rp2.viewModel.AppViewModel
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.text.Text
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun ImageCaptureScreen(
    navController: NavController,
    appViewModel: AppViewModel
)  {
    CaptureImageContainer(navController = navController, appViewModel)
}

fun Context.createImageFile2(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}


@Composable
fun CaptureImageContainer(
    navController: NavController,
    appViewModel: AppViewModel
) {

    val context = LocalContext.current
    val file = context.createImageFile2()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context.applicationContext),
        BuildConfig.APPLICATION_ID + ".fileprovider",
        file
    )

    val textRec = TextRecognizer()
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { result ->
        if (result) {
            appViewModel.updateUri(uri)
            val textTask: Task<Text> = textRec.getResultText(context, appViewModel.capturedImageUri)
            textTask.addOnSuccessListener { visionText ->
                appViewModel.updateTexto(visionText.text)
            }
                .addOnFailureListener() { e ->
                    e.printStackTrace()
                }

            navController.navigate("ResultScreen")
        } else {
            Toast.makeText(context, "Falha ao capturar a imagem", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleOnCaptureImageClick() {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

            Column {
                Row(Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.width(width = 36.dp)
                    )

                }
                Box(
                    Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Capturar imagem",
                        fontSize = 24.sp,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_6),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(alignment = Alignment.Center)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 40.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Capture uma nova imagem ou selecione da galeria",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = { handleOnCaptureImageClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(android.graphics.Color.parseColor("#4E77F8")),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Capturar imagem",
                        color = Color.White,
                        fontSize = 20.sp,
                    )

                }

                val getImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) {uri: Uri? ->
                    if(uri != null){
                        appViewModel.capturedImageUri = uri!!
                        navController.navigate("ResultScreen")
                        val textTask: Task<Text> = textRec.getResultText(context, appViewModel.capturedImageUri)
                        textTask.addOnSuccessListener { visionText ->
                            appViewModel.updateTexto(visionText.text)
                        }
                            .addOnFailureListener() { e ->
                                e.printStackTrace()
                            }
                    }
                }

                Button(
                    onClick = { getImage.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(android.graphics.Color.parseColor("#4E77F8")),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 24.dp, end = 24.dp)
                ) {
                    Text(
                        text = "Selecionar da galeria",
                        color = Color.White,
                        fontSize = 20.sp,
                    )

                }

        }

}


@Preview(showBackground = true)
@Composable
fun ImageCaptureScreenPreview() {

    val navController = rememberNavController()
    val appViewModel = AppViewModel()

    RP2Theme {
        ImageCaptureScreen(navController = navController, appViewModel)
    }
}