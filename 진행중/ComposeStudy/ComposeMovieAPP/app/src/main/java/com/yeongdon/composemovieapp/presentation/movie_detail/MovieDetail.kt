package com.yeongdon.composemovieapp.presentation.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.yeongdon.composemovieapp.R
import com.yeongdon.composemovieapp.data.remote.model.MovieDto
import com.yeongdon.composemovieapp.presentation.movie_list.MovieListViewModel


@Composable
fun MovieDetail(
    viewModelDetail: MovieDetailViewModel = hiltViewModel(),
    viewModelList: MovieListViewModel = hiltViewModel()
) {
    val state = viewModelDetail.stateApi.value

    Box(modifier = Modifier.fillMaxSize()) {
        state.movie?.let { movie ->

            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp)) {
                item {
                    MovieCard(movie = movie,
                        imdbClicked = {

                        }
                    )
                }
            }
        }
        if (state.error.isNotBlank())  {
            Text(text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieDto,
    modifier: Modifier = Modifier,
    imdbClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)) {
            GlideImage(
                imageModel = movie.Poster,
                modifier = Modifier
                    .weight(0.8f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "${movie.Title}.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = colorResource(R.color.dark_blue)
                )
                Text(
                    text = movie.Year,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = colorResource(R.color.dark_blue)

                )
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Text(
                    text = "영화 장르 : " + movie.Genre,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "메타스코어 : " + movie.Metascore,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "영화 등급: " +movie.Rated,
                    fontSize = 14.sp,
                )
                Text(
                    text = "상영시간 : " +movie.Runtime,
                    fontSize = 14.sp,
                )
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxSize()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Spacer(modifier = Modifier.weight(2f))
                }
            }
        }
    }
}