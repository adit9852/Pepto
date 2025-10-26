package com.example.pepto.data.model.locations

data class NominationResponse(
    val place_id: Long? = null,
    val licence: String? = null,
    val osm_type: String? = null,
    val osm_id: Long? = null,
    val lat: String? = null,
    val lon: String? = null,
    val category: String? = null,
    val type: String? = null,
    val place_rank: Int? = null,
    val importance: Double? = null,
    val addresstype: String? = null,
    val name: String? = null,
    val display_name: String? = null,
    val boundingbox: List<String>? = null,
// These address fields are for reverse geocoding
    val address: Address? = null
)