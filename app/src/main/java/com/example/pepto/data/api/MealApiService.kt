package com.example.pepto.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php")
    suspend fun  searchMeals(@Query("s")  SearchQuery : String) : MealsResponse

    @GET("api/json/v1/1/lookupo.php")
    suspend fun getMealById(@Query("i") id : String) : MealResponse

        @GET("categories.php")
        suspend fun getCategories() : CategoryResponse

@GET("filter.php")
suspend fun getMeealsByCategory(@Query("c") category : String) : MealsResponse

@GET("random.php")
suspend fun getRandomMeal() : MealResponse










}