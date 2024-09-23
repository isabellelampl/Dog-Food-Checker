package no.uio.ifi.in2000.isabelrl.test.dogfood.ui.home

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

data class HomeUiState(
    val foods: List<Food> = emptyList(),
    val searchSuggestions: List<Food> = emptyList()
)

class HomeScreenViewModel : ViewModel() {

    private val foodRepository: FoodRepository = FoodRepositoryImpl()

    private val _homeUiState = MutableStateFlow(HomeUiState())

    // Public immutable state flow to expose the UI state to the JokeScreen.
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    /**
     * Initialize fetching of jokes when the ViewModel is created.
     */
    init {
        loadFoods()
    }

    private fun loadFoods() {
        // Do an asynchronous operation to fetch jokes on another thread (Dispatchers.IO)
        viewModelScope.launch(Dispatchers.IO) {
            // Update the private mutableStateFlow with the loaded list of jokes
            _homeUiState.update { currentHomeUiState ->

                //val foods = foodRepository.getFoods()

                val foods = foodRepository.fakeFoods()
                currentHomeUiState.copy(foods = foods)
            }
        }
    }

    fun updateSearchSuggestions(text: String) {
        _homeUiState.update { currentHomeUiState ->
            val suggestions = currentHomeUiState.foods.filter {
                //it.name.contains(text, ignoreCase = true)
                it.name.startsWith(text, ignoreCase = true)
            }
            currentHomeUiState.copy(searchSuggestions = suggestions)
        }
    }

    fun updateSearchSuggestionsWhenEmpty(text: String) {
        _homeUiState.update { currentHomeUiState ->
            val suggestions = currentHomeUiState.foods.filter {
                //it.name.contains(text, ignoreCase = true)
                it.name.contains(text, ignoreCase = true)
            }
            currentHomeUiState.copy(searchSuggestions = suggestions)
        }
    }
}