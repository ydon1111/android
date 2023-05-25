package com.yeongdon.composevisitiedcitiesapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun TestFab() {
    val context = LocalContext.current

    FloatingActionButton(onClick = { makeToast(context, "플로팅버튼 ") }) {
        Icon(Icons.Default.Add, null)
    }

}