package com.yeongdon.composevisitiedcitiesapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestTopAppBar() {
    val context = LocalContext.current
    val icon1 = painterResource(id = R.drawable.ic_fav)
    val icon2 = painterResource(id = R.drawable.ic_book)
    val icon3 = painterResource(id = R.drawable.ic_download)
    val showMenu = remember {
        mutableStateOf(false)
    }

    TopAppBar(title = { Text(text = "Top App Bar") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Menu, null)
            }
        },
        actions = {
            IconButton(onClick = { makeToast(context, "아이콘 눌림") }) {
                Icon(painter = icon1, contentDescription = null)
            }
            IconButton(onClick = { showMenu.value = true }) {
                Icon(Icons.Default.MoreVert, null)
            }

            DropdownMenu(expanded = showMenu.value, onDismissRequest = { showMenu.value = false }) {
                DropdownMenuItem(
                    text = { Icon(painter = icon2, contentDescription = null) },
                    onClick = { makeToast(context, "book 아이콘 눌림") })
                DropdownMenuItem(
                    text = { Icon(painter = icon3, contentDescription = null) },
                    onClick = { makeToast(context, "다운로드 아이콘 눌림") })

            }

        }
    )
}