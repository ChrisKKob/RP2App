package com.example.rp2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.example.rp2.R
import com.example.rp2.ui.theme.RP2Theme
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ResultScreen(
    navController: NavController,
    text : String
){
    Column(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        .fillMaxSize()){
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .padding(start = 0.dp, top = 6.dp, end = 7.dp, bottom = 6.dp)
                    .clickable { },
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
        TabScreen(navController, text)
    }
}

@Composable
fun TabScreen(
    navController: NavController,
    text: String
) {
    var tabIndex by remember { mutableIntStateOf(1) }

    val tabs = listOf("Imagem", "Texto")

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabRowDefaults.Indicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
            color = Color(0xFF4834D4)
        )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                //contentColor = MaterialTheme.colorScheme.,
                modifier = Modifier.padding(top = 15.dp),
                indicator = indicator
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(title,
                            style = TextStyle(
                                color = Color(0xFF4834D4),
                                fontWeight = FontWeight(500),
                                fontSize = 25.sp,
                            ))

                        },
                    )
                }
            }


        when (tabIndex) {
            0 -> TabImage()
            1 -> TabText(navController, text)
        }
    }
}

@Composable
fun TabImage() {
    val painter = painterResource(id = R.drawable.lousa2)
    val zoomState = rememberZoomState(contentSize = painter.intrinsicSize)


    var contrast by remember { mutableStateOf(1f) }
    val colorMatrix = floatArrayOf(
        contrast, 0f, 0f, 0f, 0f,
        0f, contrast, 0f, 0f, 0f,
        0f, 0f, contrast, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    )


    Column(   modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        .fillMaxSize()) {
        Image(

            painter = painterResource(id = R.drawable.lousa2),
            contentDescription = "Imagem",
            colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .zoomable(zoomState),
        )

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
                colors = ButtonDefaults.filledTonalButtonColors( Color(0xFF4834D4)),
                modifier = Modifier.padding(start = 10.dp, end = 7.dp)
                    .width(80.dp)
            ) {
                Text("+",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Default,
                        color = Color(0xFFFFFFFF))
                )
            }

            Button(
                onClick = { contrast -= 0.1f },
                colors = ButtonDefaults.filledTonalButtonColors( Color(0xFF4834D4)),
                modifier = Modifier.width(80.dp)
            ) {
                Text("—",
                    style = TextStyle(
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(300),
                        color = Color(0xFFFFFFFF))
                )
            }
        }
        Box( modifier = Modifier
            .height(300.dp)
            .padding(top = 70.dp)
            .clip(shape = RoundedCornerShape(size = 25.dp))
            .background(Color(0xFF8E80EE))
            .fillMaxWidth()) {
            Text(
                text = "Além de alterar o contraste, você pode manipular o zoom com movimentos de pinça como usualmente é feito em fotos nos smartphones atuais. Use as configurações de zoom e contraste que preferir!",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500)
                ),modifier = Modifier.padding(10.dp, top = 10.dp, end = 10.dp)
            )
        }
    }
}



@Composable
fun TabText(
    navController: NavController,
    text: String
) {
    val scrollState = rememberScrollState()

    val sliderState = remember { mutableStateOf(0f) }
    val text = text
    Slider(
        value = sliderState.value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF4834D4),
            activeTrackColor = Color(0xFF4834D4),
            inactiveTrackColor =  Color(0xFFD4CDFE),
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
            text = text,
            style = TextStyle(color = Color.White),
            fontSize = (sliderState.value + 1)  * 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
        )

    }

    Button(onClick = { navController.navigate("ImageCaptureScreen") } ,
        colors = ButtonDefaults.filledTonalButtonColors( Color(0xFF4834D4)),
        modifier = Modifier.padding(start = 160.dp, top = 10.dp)
    ) {
        Text(
            text = "Gerar novamente",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily.Default,
                color = Color(0xFFFFFFFF)
            ))
    }

}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {

    val navController = rememberNavController()
    val text = "SORVETE"

    RP2Theme {
        ResultScreen(navController = navController, text)
    }
}