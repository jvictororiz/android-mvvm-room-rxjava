package com.example.myapp.domain

class Character(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: Location,
    var image: String,
    var episode: List<String>,
    var url: String
)