package no.uio.ifi.in2000.isabelrl.test.dogfood.ui.food

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun FoodScreen(navController: NavHostController, foodName: String, foodScreenViewModel: FoodScreenViewModel = viewModel()) {



    //foodScreenViewModel.initialize(foodName!!)

    LaunchedEffect(foodName) {
        foodScreenViewModel.loadFoodInfo(foodName)
    }


    val foodUiState: FoodUiState by foodScreenViewModel.foodUiState.collectAsState()
    val color = Color(android.graphics.Color.parseColor("#ece9e4"))

    if (foodUiState is FoodUiState.Success) {

        Scaffold(
            topBar = tilbakeKnapp(navController, color),
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Spacer(modifier = Modifier.size(8.dp))


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(43.dp)
                            .background(color)
                    ) {

                        Text(
                            text = (foodUiState as FoodUiState.Success).food.name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.size(25.dp))


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp)
                            .background(color)
                    ) {}

                    Spacer(modifier = Modifier.size(34.dp))

                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                    ) {

                        Text(
                            //modifier = Modifier
                            //    .padding(15.dp),
                            text = (foodUiState as FoodUiState.Success).food.information,
                            fontSize = 15.sp,
                            //fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }

        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tilbakeKnapp(navController: NavHostController, farge: Color): @Composable () -> Unit = {
    TopAppBar(
        title = { Text("", color = Color.Black, fontSize = 17.sp, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Tilbake-pil")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = farge,
            titleContentColor = Color.Black,
        )
    )
}


