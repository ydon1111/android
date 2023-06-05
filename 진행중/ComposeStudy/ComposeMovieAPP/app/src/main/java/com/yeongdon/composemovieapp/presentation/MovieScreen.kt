@file:OptIn(ExperimentalMaterial3Api::class)

package com.yeongdon.composemovieapp.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yeongdon.composemovieapp.R
import com.yeongdon.composemovieapp.common.Constants
import com.yeongdon.composemovieapp.presentation.movie_list.MovieListViewModel

sealed class MovieScreen(val screen_route: String) {
    object Main : MovieScreen("Main_screen")
    object Detail : MovieScreen("detail_screen")

}

@Composable
fun OmdbApiAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "뒤로 가기"
                    )
                }
            }
        }
    )
}

@Composable
fun OmdbApiApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val viewModel = viewModel<MovieListViewModel>()
    var movieName by remember { mutableStateOf("") }

    val context = LocalContext.current


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = getTitleByRoute(context, MovieScreen.Main.screen_route),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_blue)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.White),
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "뒤로 가기"
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier =Modifier.fillMaxWidth(),
                        value = movieName,
                        onValueChange = {newValue ->
                            movieName = newValue
                            viewModel.textData = newValue
                            viewModel.getMovies(2,Constants.URL_API_KEY)
                                        },
                        label = { Text(text = "영화검색") }
                    )

                    //TODO("클릭시 동작하게 해야함")
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(
//                            imageVector = Icons.Filled.Search,
//                            contentDescription = "검색"
//                        )
//                    }

                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}

fun getTitleByRoute(context: Context, route: String): String {
    return when (route) {
        "main_screen" -> context.getString(R.string.movie_main)
        "detail_screen" -> context.getString(R.string.movie_detail)
        else -> context.getString(R.string.app_name)
    }
}


//@Composable
//fun OnIconSearchClick() {
//    val searchText = remember {
//        mutableStateOf("")
//    }
//    OutlinedTextField(
//        value = searchText.value,
//        onValueChange = { searchText.value = it },
//        label = { Text(text = "영화검색") }
//    )
//}




