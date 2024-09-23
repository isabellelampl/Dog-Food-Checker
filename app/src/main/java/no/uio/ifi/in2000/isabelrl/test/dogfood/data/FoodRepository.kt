package no.uio.ifi.in2000.isabelrl.test.dogfood.data

import android.util.Log

interface FoodRepository {
    suspend fun getFoods(): List<Food>
    suspend fun fakeFoods(): List<Food>
    suspend fun findFood(foodName: String): Food
}

class FoodRepositoryImpl (private val foodDataSource: FoodDataSource = FoodDataSource()) : FoodRepository {

    override suspend fun getFoods(): List<Food> {
        return foodDataSource.fetchFoods()
    }

    override suspend fun fakeFoods(): List<Food> {
        return listOf(Food("Gulrot", "Dette kan hunden spise"), Food("Løk", "Dette er giftig"), Food("Sjokolade", "Hunden din kan dø"), Food("Salat", "Vegan dog"), Food("Peanøtt", "Gifitig"))
    }

    override suspend fun findFood(foodName: String): Food {
        Log.d("Repository", "foodname = $foodName")
        val food = foodName.lowercase()

        Log.d("Repository", "food = $food")

        val foods = fakeFoods()

        Log.d("Repository", "foods = $foods")

        val filteredList = foods.filter {it.name.lowercase() == food}

        Log.d("Repository", "Henter ut denne ${foods.first()}")

        return filteredList.first()

    }
}