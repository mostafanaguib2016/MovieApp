package com.example.movieapp.data

import com.example.movieapp.pojo.DetailsResponse
import com.example.movieapp.pojo.ImagesResponse
import com.example.movieapp.pojo.PersonsResponse
import com.example.movieapp.pojo.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServices
{
    @GET("person/popular")
    suspend fun getPersons(
        @Query("api_key") api_key: String
    ): Response<PersonsResponse>

    @GET("person/{id}")
    suspend fun getDetails(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<DetailsResponse>


    @GET("person/{id}/images")
    suspend fun getImages(
        @Path("id") id: String,
        @Query("api_key") api_key: String
    ): Response<ImagesResponse>


}