package com.example.rp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab

import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.times


import com.example.rp2.ui.theme.RP2Theme
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

class ResultsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RP2Theme {
                Column(modifier = Modifier.padding(start=16.dp,end=16.dp, top=16.dp).fillMaxSize()){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val mContext = LocalContext.current

                        Image(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .padding(start = 0.dp, top = 6.dp, end = 7.dp, bottom = 6.dp)
                                .clickable { mContext.startActivity(Intent(mContext, CaptureImageActivity2::class.java)) },
                            painter = painterResource(id = R.drawable.east),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "Transcrição",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF1D1B20),
                            )
                        )
                    }
                    TabScreen()
                }
            }
        }
    }
}


@Composable
fun TabScreen() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Image", "Text")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    modifier = Modifier
                        .background(Color.White)
                        .clip(shape = RoundedCornerShape(size = 25.dp)),

                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            1 -> TabImage()
            0 -> TabText()
        }
    }
}

@Composable
fun TabImage() {
    val painter = painterResource(id = R.drawable.img)
    val zoomState = rememberZoomState(contentSize = painter.intrinsicSize)

    Image(

        painter = painterResource(id = R.drawable.img),
        contentDescription = "Imagem",
        modifier = Modifier.fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .zoomable(zoomState),

    )


}




@Composable
fun TabText() {
    // Conteúdo da aba "Texto"
    val scrollState = rememberScrollState()

    val sliderState = remember { mutableStateOf(0f) }
    val text = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA  AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA A AAAAAAAAAAAAAAAAAAAAA"
    Slider(
        value = sliderState.value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF4834D4),
            activeTrackColor = Color(0xFF4834D4),
            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        onValueChange = { sliderState.value = it }
    )
    Box(
        modifier = Modifier.width(400.dp)
            .height(600.dp)
            .background(Color(0xFF2B2B2B))
            .clip(shape = RoundedCornerShape(size = 25.dp))
            .verticalScroll(scrollState)
            .fillMaxWidth()

        ) {
        Text(
            text = text,
            style = TextStyle(color = Color.White),
            fontSize = (sliderState.value + 1)  * 36.sp,
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
        )

        }

    Button(onClick = { /* Do something! */ } ,
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
fun PreviewResultsActivity() {
    RP2Theme {
        Column(modifier = Modifier.padding(start=16.dp,end=16.dp, top=16.dp).fillMaxSize()){
            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val mContext = LocalContext.current

                Image(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .padding(start = 0.dp, top = 6.dp, end = 7.dp, bottom = 6.dp)
                        .clickable { mContext.startActivity(Intent(mContext, CaptureImageActivity2::class.java)) },
                    painter = painterResource(id = R.drawable.east),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                    )

                Text(
                    text = "Transcrição",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF1D1B20),
                    )
                )
            }
            TabScreen()
        }
    }
}


