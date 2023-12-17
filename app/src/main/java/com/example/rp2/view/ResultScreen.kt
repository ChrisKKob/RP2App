package com.example.rp2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.rp2.R
import com.example.rp2.ui.theme.RP2Theme
import com.example.rp2.viewModel.AppViewModel
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ResultScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.img_7),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopStart
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable {
                        navController.popBackStack()
                        navController.navigate("ImageCaptureScreen")
                    },
                painter = painterResource(id = R.drawable.img_9),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Resultado da captura",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    color = Color(android.graphics.Color.parseColor("#ffffff")),
                )
            )
        }
        TabScreen(navController, appViewModel)
    }


//

}

@Composable
fun TabScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Imagem", "Texto")

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        Box {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(
                    Color.White
                )
                .padding(4.dp),
            indicator = indicator,
            divider = {},
            containerColor = Color.White,
            contentColor = Color.White,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    modifier = if (tabIndex == index) Modifier
                        .clip(RoundedCornerShape(100))
                        .background(
                            Color(android.graphics.Color.parseColor("#4E94FA"))
                        )
                    else Modifier
                        .clip(RoundedCornerShape(100))
                        .background(
                            Color.White
                        ),
                    text = {
                        Text(
                            text = title,
                            color = if (tabIndex != index) Color(android.graphics.Color.parseColor("#4E94FA")) else Color.White
                        )
                    },
                )
            }
        }

        when (tabIndex) {
            0 -> TabImage(appViewModel)
            1 -> TabText(navController, appViewModel)
        }
    }
}

@Composable
fun TabImage(
    appViewModel: AppViewModel
) {
    val painter = rememberImagePainter(data = appViewModel.capturedImageUri)
    val zoomState = rememberZoomState(contentSize = Size.Zero)

    var contrast by remember { mutableStateOf(1f) }
    val colorMatrix = floatArrayOf(
        contrast, 0f, 0f, 0f, 0f,
        0f, contrast, 0f, 0f, 0f,
        0f, 0f, contrast, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    )


    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ajustar contraste",
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                modifier = Modifier.padding(10.dp)
            )
            Button(
                shape = CircleShape,
                onClick = { contrast += 0.1f },
                colors = ButtonDefaults.filledTonalButtonColors(
                    Color(
                        android.graphics.Color.parseColor(
                            "#4E94FA"
                        )
                    )
                )
            ) {
                Text(
                    "+",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
            }

            Button(
                onClick = { contrast -= 0.1f },
                shape = CircleShape,
                colors = ButtonDefaults.filledTonalButtonColors(
                    Color(
                        android.graphics.Color.parseColor(
                            "#4E94FA"
                        )
                    )
                ),
            ) {
                Text(
                    "—",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painter,
            contentDescription = "Imagem",
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, 700.dp)
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .zoomable(zoomState),
        )

    }
}


@Composable
fun TabText(
    navController: NavController,
    appViewModel: AppViewModel
) {
    val sliderState = remember { mutableStateOf(0f) }
    Spacer(modifier = Modifier.height(48.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                value = sliderState.value,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = SliderDefaults.colors(
                    thumbColor = Color(android.graphics.Color.parseColor("#007FFF")),
                    activeTrackColor = Color(android.graphics.Color.parseColor("#007FFF")),
                    inactiveTrackColor = Color(android.graphics.Color.parseColor("#C4E1FF")),
                ),
                onValueChange = { sliderState.value = it }
            )
            Spacer(Modifier.weight(0.5f))

            Image(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable {
                        navController.popBackStack()
                        navController.navigate("ImageCaptureScreen")
                    },
                painter = painterResource(id = R.drawable.img_10),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 400.dp)
                .clip(shape = RoundedCornerShape(size = 16.dp))
                .background(Color(0xFF2B2B2B))
        ) {
            Text(
                text = appViewModel.texto,
                style = TextStyle(color = Color.White),
                fontSize = (sliderState.value + 1) * 24.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


    }
}


@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {

    val navController = rememberNavController()
    val appViewModel = AppViewModel()

    RP2Theme {
        ResultScreen(navController = navController, appViewModel)
    }
}