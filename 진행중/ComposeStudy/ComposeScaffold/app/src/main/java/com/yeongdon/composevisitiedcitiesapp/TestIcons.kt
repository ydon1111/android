package com.yeongdon.composevisitiedcitiesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun TestIcons() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val icon1 = painterResource(id = R.drawable.ic_fav)
        Icon(painter = icon1, contentDescription = null )

        Icon(Icons.Default.Star,null)

        IconButton(onClick ={ } ) {
            Icon(Icons.Default.Person,null)
        }
    }
}