package com.example.rp2

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.rp2.ui.theme.RP2Theme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

class CaptureImageActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RP2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {

                        Text(
                            text = "Análise de imagem",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                        )
                        CaptureImageContainer()
                    }
                }
            }
        }
    }
}


@Composable
fun CaptureImageContainer() {
    val context = LocalContext.current
    val file = context.createImageFile2()
    var uri: Uri = Uri.EMPTY
    try {
        uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context.applicationContext),
            "com.example.rp2.fileprovider", file
        );
    } catch (e: Exception) {

    }


    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
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

    val textRec = TextRecognizer()
    var text by remember { mutableStateOf("") }

    if (capturedImageUri.path?.isNotEmpty() == true) {
        context.startActivity(Intent(context, ResultsActivity::class.java))
        /*  val textTask: Task<Text> = textRec.getResultText(context, capturedImageUri)
          textTask.addOnSuccessListener { visionText ->
              text = visionText.text
          }
              .addOnFailureListener() { e ->
                  e.printStackTrace()
              }


          Image(
              modifier = Modifier
                  .padding(8.dp, 6.dp),
              painter = rememberImagePainter(capturedImageUri),
              contentDescription = null
          )
          Text(
              text = text
          )*/
    }

    fun handleOnCaptureImageClick() {
        val permissionCheckResult = (
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                )
        if (permissionCheckResult) {
            cameraLauncher.launch(uri)
        } else {
            // Request a permission
            permissionLauncher.launch(Manifest.permission.CAMERA)
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    Image(

        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .fillMaxHeight()
            .clickable { handleOnCaptureImageClick() },
        painter = painterResource(id = R.drawable.img_1),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )

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

@Preview(showBackground = true)
@Composable
fun PreviewCaptureImage2Activity() {
    RP2Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {

                Text(
                    text = "Análise de imagem",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                )
                CaptureImageContainer()
            }
        }
    }
}







