package com.example.pepto.data.api

import com.example.pepto.data.model.locations.NominationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface  NominationService{
    @GET("reverse")
    suspend fun reverseGeocode(
        @Query("lat") latitude : Double,
        @Query("lon")  longitude : Double,
        @Query("format") format :String = "json",
        @Query("adressdetails") addressDetails : Int = 1,
        @Header ("User-Agent") userAgent :String = "Pepto/1.0(adityakuamr8018@gmail.com)"

    ) : NominationResponse

    @GET("search")
    suspend fun searchNearby(
        @Query("q") query: String,
        @Query("format") format : String = "json",
        @Query("adressdetails") addressDetails : Int = 1,
        @Query("limit") limit : Int = 5,
        @Header("User-Agent") userAgent: String = "PeptoApp/1.0()adityakumar8018@gmail.com") : List<NominationResponse>


    @GET("search")

    suspend fun searchInBoundingBox(
        @Query("q") query: String ,
       @Query("format") format: String = " json",
        @Query("adressdetails") addressDetails: Int = 1,
        @Query("Viewbox") viewbox : String,
        @Query("bounded") bounded : Int = 1,
        @Header("user-Agent") userAgent: String = "PeptoApp/1.0(adityakumar8018@gmail.com)" ): List<NominationResponse>





}