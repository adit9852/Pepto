package com.example.pepto.data.model.mealDB

import com.example.pepto.data.model.fakeApi.Product
import com.example.pepto.data.model.fakeApi.Rating
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class MealResponse(
    @SerializedName("meals")
    val meals : List<MealsDto>
)
data class CategoryResponse(
    @SerializedName("categories")
    val categories : List<CategoryDto>

)

data class MealsDto(

    @SerializedName("idMeal")
    val id: String,

    @SerializedName("strMeal")
    val name: String,

    @SerializedName("strCategory")
    val category: String,

    @SerializedName("strArea")
    val area: String,
    @SerializedName("strInstructions")
    val instructions: String,


    @SerializedName("strMealThumb")
    val thumbnailUrl: String,

    @SerializedName("strTags")
    val tags: String




) {
    fun toProduct(): Product {
        val randomPrice = Random.nextDouble(50.0, 500.0) // as there is no meal pricing in the api


        val randomRating =  Random.nextDouble(1.0,5.0)

        val randomViewCount = Random.nextInt(100,999)

        return Product(
            id  =id.toInt(),
            name = name,
            price = randomPrice,
            category = category ?: " ",
            imageUrl = thumbnailUrl ?: " ",
            imageRes = 0 , //Mo local source
            description = instructions ?: " ",
            rating = Rating(rate = randomRating, count = randomViewCount)
        )

    }

}

data class CategoryDto (

    @SerializedName("idCategory")
    val id : String,

    @SerializedName("strCategory")
    val name : String,

    @SerializedName("strCategoryThumb")
    val imageUrl : String,

    @SerializedName("strCategoryDescription")
    val description: String
) {
    fun toCategory(): MealCategory {
        return MealCategory(
            id = id,
            name = name,
            imageRes = imageUrl
        )
    }
}
