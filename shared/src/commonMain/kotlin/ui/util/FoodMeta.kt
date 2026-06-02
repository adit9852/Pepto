/**
 * Copyright 2023 Aditya Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui.util

/**
 * The remote API (a food blog) only provides title/author/body/image. To make the UI feel like a
 * real food-delivery app, we synthesise stable, realistic-looking metadata (rating, delivery time,
 * price, cuisine, distance, offers) deterministically from the item's [id] so the values never
 * change between recompositions or app launches.
 */
data class FoodMeta(
    val rating: String,
    val ratingCount: String,
    val deliveryTime: String,
    val priceForTwo: String,
    val cuisine: String,
    val distance: String,
    val offer: String?,
    val isPureVeg: Boolean,
)

private val cuisines = listOf(
    "North Indian • Chinese",
    "Italian • Pizza",
    "Burgers • Fast Food",
    "Biryani • Mughlai",
    "South Indian • Dosa",
    "Desserts • Bakery",
    "Sushi • Asian",
    "Healthy • Salads",
    "Cafe • Beverages",
    "Rolls • Street Food",
)

private val offers = listOf(
    "50% OFF up to ₹100",
    "Free delivery",
    "₹125 OFF above ₹249",
    "Buy 1 Get 1 Free",
    "20% OFF on all items",
)

fun foodMetaFor(id: Int): FoodMeta {
    val seed = (id * 2654435761u.toInt())
    fun pick(salt: Int, range: Int) = ((seed xor (salt * 0x9E3779B1.toInt())) and 0x7FFFFFFF) % range

    val rating = 36 + pick(1, 14) // 3.6 .. 4.9
    val ratingDisplay = "${rating / 10}.${rating % 10}"

    val reviews = 120 + pick(2, 4800)
    val ratingCount = if (reviews >= 1000) "${reviews / 1000}.${(reviews % 1000) / 100}K" else "$reviews"

    val baseTime = 18 + pick(3, 27) // 18 .. 44
    val deliveryTime = "$baseTime-${baseTime + 7} min"

    val price = (1 + pick(4, 6)) * 100 // ₹100 .. ₹600
    val priceForTwo = "₹$price for two"

    val distanceTenths = 4 + pick(5, 56) // 0.4 .. 6.0 km
    val distance = "${distanceTenths / 10}.${distanceTenths % 10} km"

    val cuisine = cuisines[pick(6, cuisines.size)]

    // ~70% of places carry an offer
    val offer = if (pick(7, 10) < 7) offers[pick(8, offers.size)] else null

    val isPureVeg = pick(9, 4) == 0 // ~25%

    return FoodMeta(
        rating = ratingDisplay,
        ratingCount = ratingCount,
        deliveryTime = deliveryTime,
        priceForTwo = priceForTwo,
        cuisine = cuisine,
        distance = distance,
        offer = offer,
        isPureVeg = isPureVeg,
    )
}
