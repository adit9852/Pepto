package com.example.pepto.data.model.fakeApi


import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.unit.IntRect
import io.ktor.util.internal.OpDescriptor

data class Product(
    val id : Int,
    val name : String,
    val price : Double,
    val imageUrl : String,
    val weight: Int = 100, //Default weight in Grams
    val imageRes : Int  = 0, // Used for local image
    val category: String?=null,
    val description : String ? = null,
    val rating : Rating? = null

)

data class Rating (
    val rate : Double , // Rating Score (eg 4.2)
    val count : Int  // No. of Ratings
)