package com.yeongdon.composevisitiedcitiesapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yeongdon.composevisitiedcitiesapp.ui.theme.ComposeVisitiedCitiesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVisitiedCitiesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateScaffold()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScaffold(){
    Scaffold(
        topBar ={ TestTopAppBar() },
        bottomBar = {TestBottomAppBar()},
        floatingActionButton = {TestFab()},
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        TestIcons()
    }
}


fun makeToast(ctx: Context, msg: String){
    Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show()
}
