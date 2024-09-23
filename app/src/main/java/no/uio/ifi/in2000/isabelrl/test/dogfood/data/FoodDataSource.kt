package no.uio.ifi.in2000.isabelrl.test.dogfood.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson

class FoodDataSource {

    private val path: String =
        ""

    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchFoods(): List<Food> {
        val response = client.get(path)
        val foods: Foods = response.body()
        return foods.foods
    }
}