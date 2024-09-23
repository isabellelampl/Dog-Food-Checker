package no.uio.ifi.in2000.isabelrl.test.dogfood.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import no.uio.ifi.in2000.isabelrl.test.dogfood.data.Food

@Composable
fun HomeScreen(navController: NavHostController, homeScreenViewModel: HomeScreenViewModel = viewModel()) {

    val foodUiState: HomeUiState by homeScreenViewModel.homeUiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showSuggestions: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {

            Text(
                text = "Kan hunden min spise ...",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,

            )

            Spacer(modifier = Modifier.size(60.dp))

            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()) {


                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            if (searchQuery.isNotEmpty()) {
                                homeScreenViewModel.updateSearchSuggestions(searchQuery)

                                if (foodUiState.searchSuggestions.isEmpty()) {
                                    homeScreenViewModel.updateSearchSuggestionsWhenEmpty(searchQuery)
                                }
                            }
                            showSuggestions = true
                        },
                        trailingIcon = {
                            val keyboardController = LocalSoftwareKeyboardController.current

                            IconButton(onClick = {
                                keyboardController?.hide()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    modifier = Modifier
                                        .size(70.dp)
                                        .padding(end = 18.dp)
                                )
                            }
                        },
                        label = { Text("Søk etter mattyper",
                                        modifier = Modifier
                                            .align(alignment = CenterStart),
                                        fontSize = 20.sp,
                                        color = Color.Black) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 30.sp, textAlign = TextAlign.Center), // Endre tekststørrelse
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp)
                            .height(120.dp)
                            .align(alignment = Alignment.Center)
                    )
                }

                if (showSuggestions) {

                    //if (foodUiState.searchSuggestions.isEmpty()) {

                    //}
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        LazyColumn(modifier = Modifier
                            .align(Alignment.Center)
                        ,content = {
                            items(foodUiState.searchSuggestions) { suggestion ->
                                TextButton(
                                    onClick = {
                                        navController.navigate("foodscreen/${suggestion.name}")
                                        showSuggestions = false
                                    }
                                ) {
                                    Text(
                                        text = suggestion.name,
                                        fontSize = 23.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        })
                    }
                }


                /*
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                //modifier = Modifier.fillMaxWidth()
            ) {
                foodUiState.searchSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        { Text(text = suggestion.name) },
                        onClick = {
                            searchQuery = suggestion.name
                            navController.navigate("foodscreen/${searchQuery}")

                            //homeScreenViewModel.updateSearchSuggestions(suggestion.name)
                            expanded = false


                        })
                }
            }

                 */

                    /*LazyColumn {
            items(foodUiState.foods) { food ->
                FoodItem(food)
            }
        }

         */

                //}
            }
        }
    }
}



@Composable
fun FoodItem(food: Food) {
    Text(text = food.name)
}


    
