package com.example.myapp.repository.network.dto

data class CharacterDTO(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: LocationDTO,
    var image: String,
    var episode: List<String>,
    var url: String
)
