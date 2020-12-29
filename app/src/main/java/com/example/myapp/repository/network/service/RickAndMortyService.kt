package com.example.myapp.repository.network.service

import com.example.myapp.repository.network.dto.RickAndMortyPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    @Headers("Content-Type: application/json")
    fun characterList(
        @Query("page") page: Int
    ): Single<RickAndMortyPage>

}