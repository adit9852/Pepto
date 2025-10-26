package com.example.pepto.data.model

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals : List<MealsDto>
)
data class CategoryResponse(
    @SerializedName("categories")
    val categories : List<CategoryDto>
)