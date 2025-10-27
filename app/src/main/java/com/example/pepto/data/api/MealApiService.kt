package com.example.pepto.data.api

import com.example.pepto.data.model.mealDB.CategoryResponse
import com.example.pepto.data.model.mealDB.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") SearchQuery: String): MealResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMeealsByCategory(@Query("c") category: String): MealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

}