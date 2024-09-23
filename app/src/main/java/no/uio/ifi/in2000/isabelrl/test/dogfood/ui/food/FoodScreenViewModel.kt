package no.uio.ifi.in2000.isabelrl.test.dogfood.ui.food

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.isabelrl.test.dogfood.data.Food
import no.uio.ifi.in2000.isabelrl.test.dogfood.data.FoodRepository
import no.uio.ifi.in2000.isabelrl.test.dogfood.data.FoodRepositoryImpl

sealed interface FoodUiState {
    data class Success(val food: Food) : FoodUiState
    data object Loading : FoodUiState
    data object Error : FoodUiState
}

class FoodScreenViewModel : ViewModel() {

    private val foodRepository: FoodRepository = FoodRepositoryImpl()

    private val _foodUiState = MutableStateFlow<FoodUiState>(FoodUiState.Loading)

    // Public immutable state flow to expose the UI state to the JokeScreen.
    val foodUiState: StateFlow<FoodUiState> = _foodUiState.asStateFlow()


    /*init {
        loadFoodInfo()
    }


     */
    fun initialize(foodName: String) {
        loadFoodInfo(foodName)
    }

    fun loadFoodInfo(foodName: String) {
        Log.d("FoodViewModel", "Kommer inn i viewmodel med $foodName som parameter")
        // Do an asynchronous operation to fetch jokes on another thread (Dispatchers.IO)
        viewModelScope.launch(Dispatchers.IO) {
            val food = foodRepository.findFood(foodName)
            Log.d("FoodViewModel", "Henter ut $food")
            // Update the private mutableStateFlow with the loaded list of jokes
            _foodUiState.update {
                Log.d("FoodViewModel", "Oppdaterer uistate med ${food.name}")
                FoodUiState.Success(food = food)
            }
        }
    }

}