package no.uio.ifi.in2000.isabelrl.test.dogfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.uio.ifi.in2000.isabelrl.test.dogfood.ui.food.FoodScreen
import no.uio.ifi.in2000.isabelrl.test.dogfood.ui.home.HomeScreen
import no.uio.ifi.in2000.isabelrl.test.dogfood.ui.theme.DoggieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoggieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {


                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "homescreen") {

                        composable(
                            //Sender med partyId her
                            route = "foodscreen/{foodName}",
                            arguments = listOf(navArgument("foodName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            FoodScreen(
                                navController,
                                backStackEntry.arguments?.getString("foodName").toString()
                            )
                        }

                        composable("homescreen") {
                            HomeScreen(navController)
                        }


                    }
                }
            }
        }
    }
}


