package com.example.rp2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rp2.R
import com.example.rp2.ui.theme.RP2Theme

@Composable
fun HomeScreen(
    navController: NavController
){
    Column(modifier = Modifier.padding(start=16.dp,end=16.dp)){

        Text(
            text = "Bem vindo, User",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
        )

        CardImage(navController)

    }
}

@Composable
fun CardImage(
    navController: NavController
) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .height(height = 196.dp),
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,

                )
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
                text = "Análise de imagem",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )


            Text(
                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 16.dp),
                text = "Converta imagens para texto, áudio, ajuste o contraste e outros!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                Button(onClick={ navController.navigate("ImageCaptureScreen")
                }){
                    Text("Conferir")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    val navController = rememberNavController()

    RP2Theme {
        HomeScreen(navController = navController)
    }
}
