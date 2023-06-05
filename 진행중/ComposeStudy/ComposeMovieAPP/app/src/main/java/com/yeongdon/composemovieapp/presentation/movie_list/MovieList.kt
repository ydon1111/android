package com.yeongdon.composemovieapp.presentation.movie_list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yeongdon.composemovieapp.presentation.MovieScreen
import com.yeongdon.composemovieapp.presentation.movie_list.components.MovieItem

@Composable
fun MovieList(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        } else if (state.movies.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onCardClick = {
                            navController.navigate(MovieScreen.Detail.screen_route + "/${movie.imdbID}")
                        }
                    )
                }
            }
        } else {
            Text(
                text = "검색 결과가 없습니다.",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}