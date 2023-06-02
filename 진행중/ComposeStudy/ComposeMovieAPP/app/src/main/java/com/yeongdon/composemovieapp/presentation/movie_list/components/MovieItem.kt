package com.yeongdon.composemovieapp.presentation.movie_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.yeongdon.composemovieapp.R
import com.yeongdon.composemovieapp.data.remote.model.Search
import com.yeongdon.composemovieapp.presentation.movie_list.MovieListViewModel


@Composable
fun MovieItem(
    movie: Search,
    onCardClick: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {
            GlideImage(
                imageModel = movie.Poster,
                modifier = Modifier
                    .weight(1.5f)
                    .width(100.dp)
                    .height(140.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart
            )
            Spacer(modifier = Modifier.weight(0.3f))

            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${movie.Title}.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier,
                    color = colorResource(id = R.color.dark_blue)
                )
                Text(
                    text = movie.Year,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = colorResource(id = R.color.dark_blue)
                )

            }

        }

    }

}