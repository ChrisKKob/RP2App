package com.example.rp2.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.rp2.R
import com.example.rp2.TextRecognizer
import com.example.rp2.ui.theme.RP2Theme
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.text.Text
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun ImageCaptureScreen(
    navController: NavController
) {
    CaptureImageContainer(navController = navController)

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
    navController: NavController
) {
    val context = LocalContext.current
    val file = context.createImageFile2()
    var uri: Uri = Uri.EMPTY
    try {
        uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context.applicationContext), "com.example.rp2.fileprovider", file
        )
    } catch (e: Exception) {
        e.message?.let { Log.d("e", it) }
    }
//
//
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        capturedImageUri = uri
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



    if (capturedImageUri.path?.isNotEmpty() == true) {
        val textRec = TextRecognizer()
        var text by remember { mutableStateOf("") }
        //context.startActivity(Intent(context, ResultsActivity::class.java))
        val textTask: Task<Text> = textRec.getResultText(context, capturedImageUri)
        textTask.addOnSuccessListener { visionText ->
            text = visionText.text
        }.addOnFailureListener() { e ->
            e.printStackTrace()
        }

        navController.navigate("ResultScreen/$text")

    }

    fun handleOnCaptureImageClick() {
        val permissionCheckResult = (ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
        if (permissionCheckResult) {
            cameraLauncher.launch(uri)
        } else {
            // Request a permission
            permissionLauncher.launch(Manifest.permission.CAMERA)
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }
    Column(
        modifier = Modifier.padding(
            start = 16.dp, end = 16.dp, bottom = 16.dp
        )
    ) {


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
                text = "Selecionar imagem",
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

    RP2Theme {
        ImageCaptureScreen(navController = navController)
    }
}