package com.example.myapp.repository.network.dto

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)

data class RickAndMortyPage(
    val info: Info,
    val results: List<CharacterDTO>
)