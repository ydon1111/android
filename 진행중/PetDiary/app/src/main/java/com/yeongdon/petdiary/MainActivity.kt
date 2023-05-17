package com.yeongdon.petdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yeongdon.petdiary.ui.theme.PetDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PetDiaryApp()
        }
    }
}


@Composable
fun PetDiaryApp(modifier: Modifier = Modifier) {
    PetDiaryTheme {
        var currentScreen: PetDiaryDestination by remember {
            mutableStateOf(Overview)
        }
        val navController = rememberNavController(navigators = )


    }


}

@Preview(showBackground = true)
@Composable
fun PetDiaryPreview() {
    PetDiaryTheme {
        PetDiary()
    }
}