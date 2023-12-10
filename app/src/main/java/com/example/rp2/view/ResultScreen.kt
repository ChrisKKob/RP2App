package com.example.rp2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
){
    Column(modifier = Modifier.background(Color.White)
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        .fillMaxSize()
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(start = 0.dp, top = 6.dp, end = 7.dp, bottom = 6.dp)
                    .clickable {navController.popBackStack()
                        navController.navigate("ImageCaptureScreen")},
                painter = painterResource(id = R.drawable.east),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Transcrição",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF1D1B20),
                )
            )
        }
        TabScreen(navController, appViewModel)
    }
}

@Composable
fun TabScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Imagem", "Texto")

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabRowDefaults.Indicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
            color = Color(android.graphics.Color.parseColor("#007FFF"))
        )
    }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.padding(top = 15.dp),
                indicator = indicator
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        modifier = Modifier.background(Color.White),
                        text = { Text(title,
                            style = TextStyle(
                                color = Color(android.graphics.Color.parseColor("#007FFF")),
                                fontWeight = FontWeight(500),
                                fontSize = 25.sp,
                            ))

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


    Column(   modifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Manipular contraste",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(400),

                ),
            modifier = Modifier.padding(10.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(
                0.dp,
                Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { contrast += 0.1f },
                colors = ButtonDefaults.filledTonalButtonColors(  Color(android.graphics.Color.parseColor("#007FFF"))),
                modifier = Modifier
                    .padding(start = 10.dp, end = 7.dp)
                    .width(80.dp)
            ) {
                Text("+",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Default,
                        color = Color.White
                )
                )
            }

            Button(
                onClick = { contrast -= 0.1f },
                colors = ButtonDefaults.filledTonalButtonColors(  Color(android.graphics.Color.parseColor("#007FFF"))),
                modifier = Modifier.width(80.dp)
            ) {
                Text("—",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(300),
                        color = Color.White
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
    val scrollState = rememberScrollState()

    val sliderState = remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        )
    {
            Slider(
                value = sliderState.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color(android.graphics.Color.parseColor("#007FFF")),
                    activeTrackColor = Color(android.graphics.Color.parseColor("#007FFF")),
                    inactiveTrackColor =  Color(android.graphics.Color.parseColor("#C4E1FF")),
                ),
                onValueChange = { sliderState.value = it }
            )
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(600.dp)
                    .clip(shape = RoundedCornerShape(size = 25.dp))
                    .background(Color(0xFF2B2B2B))
                    .verticalScroll(scrollState)
                    .fillMaxWidth()

            ) {
                Text(
                    text = appViewModel.texto,
                    style = TextStyle(color = Color.White),
                    fontSize = (sliderState.value + 1)  * 36.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                )

            }

        Button(onClick = {
            navController.popBackStack()
            navController.navigate("ImageCaptureScreen")
        } ,
            colors = ButtonDefaults.filledTonalButtonColors(Color(android.graphics.Color.parseColor("#007FFF"))),
            modifier = Modifier.padding(start = 160.dp, top = 10.dp)
        ) {
            Text(
                text = "Gerar novamente",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily.Default,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

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