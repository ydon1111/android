@file:OptIn(ExperimentalMaterial3Api::class)

package com.yeongdon.composevisitiedcities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeongdon.composevisitiedcities.ui.theme.ComposeVisitiedCitiesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVisitiedCitiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val cities = remember {
                        mutableListOf<City>()
                    }
                    val sort = remember {
                        mutableStateOf(false)
                    }
                    val sortedCities = if (sort.value) cities.sortedBy { it.name[0] } else cities

                    CitiesList(
                        sortedCities,
                        onAddCity = { name, country ->
                            cities.add(City(name, country))
                        },
                        onSort = {
                            sort.value = !sort.value
                        })
                }
            }
        }
    }
}

data class City(
    val name: String,
    val country: String
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesList(
    list: List<City>,
    onAddCity: (name: String, country: String) -> Unit,
    onSort: () -> Unit
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            AddCityFab {
                showDialog.value = true
            }
        },
        topBar = {
            AddTopBar(onSort)
        }
    ) {
        if (list.isEmpty())
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("방문한 도시가 없습니다.")
            }
        else
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
                items(list) {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xffeeeeee))
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = it.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = it.country)
                    }
                }
            }
    }

    if (showDialog.value) {
        AddCityDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = { name, country ->
                showDialog.value = false
                onAddCity.invoke(name, country)
            }
        )
    }
}


@Composable
fun AddTopBar(onSort: () -> Unit) {
    val icon = painterResource(id = R.drawable.ic_sort)
    TopAppBar(
        title = { Text(text = "방문도시") },
        actions = {
            IconButton(onClick = onSort) {
                Icon(painter = icon, contentDescription = null)
            }
        }
    )
}


@Composable
fun AddCityFab(onFabClick: () -> Unit) {
    FloatingActionButton(onClick = onFabClick) {
        Icon(Icons.Default.Add, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityDialog(onDismiss: () -> Unit, onConfirm: (name: String, country: String) -> Unit) {
    val cityName = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val country = remember {
        mutableStateOf(TextFieldValue(""))
    }


    AlertDialog(
        onDismissRequest = { onDismiss },
        title = {
            Text(
                text = "Add a city",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = cityName.value,
                    onValueChange = { cityName.value = it },
                    label = { Text(text = "City name") },
                    placeholder = { Text(text = "Seoul") },
                    modifier = Modifier.padding(5.dp)
                )
                TextField(
                    value = country.value,
                    onValueChange = { country.value = it },
                    label = { Text(text = "Country") },
                    placeholder = { Text(text = "korea") },
                    modifier = Modifier.padding(5.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val n = cityName.value.text
                val c = country.value.text

                if (n.isNotEmpty() && c.isNotEmpty())
                    onConfirm.invoke(n, c)
            }) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}