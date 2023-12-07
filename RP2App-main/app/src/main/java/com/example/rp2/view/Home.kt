package com.example.rp2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier.width(width = 36.dp)
            )

        }


        Row(Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {
            Text(
                text = "Bem vindo, ",
                fontSize = 24.sp,
            )
            Text(
                text = "Nicolas",
                fontSize = 24.sp,
                color = Color(android.graphics.Color.parseColor("#007FFF")),
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.End)
                .padding(top = 32.dp, start = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }

        Text(
            text = "Capture",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp)
        )
        Text(
            text = "imagens",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        )

        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "e converta seu conteúdo para texto e áudio!",
                fontSize = 20.sp,
                modifier = Modifier
                    .alignByBaseline()
                    .fillMaxWidth(0.8f)
            )

            Image(
                painter = painterResource(id = R.drawable.img_5),
                contentDescription = null,
                modifier = Modifier
                    .width(width = 56.dp)
                    .clickable { navController.navigate("ImageCaptureScreen") }
            )
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
