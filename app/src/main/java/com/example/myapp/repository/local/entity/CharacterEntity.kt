package com.example.myapp.repository.local.entity

import androidx.room.*

data class Location(
    var name: String,
    var url: String
)

@Entity(tableName = "characteres")
data class CharacterEntity(
    @PrimaryKey
    val id: Int = 0,

    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,

    @Embedded(prefix = "loc_")
    val origin: Location,

    val image: String,
    val url: String
)

data class CharacterWithEpisodes(
    @Embedded val character: CharacterEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val episodes: List<EpisodeEntity>
)