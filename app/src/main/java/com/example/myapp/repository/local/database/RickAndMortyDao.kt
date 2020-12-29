package com.example.myapp.repository.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapp.repository.local.entity.CharacterEntity
import com.example.myapp.repository.local.entity.CharacterWithEpisodes
import com.example.myapp.repository.local.entity.EpisodeEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RickAndMortyDao {

    @Transaction
    @Query("Select * from characteres")
    fun getCharacters(): Single<List<CharacterWithEpisodes>>

    @Insert
    fun addCharacter(character: CharacterEntity)

    @Insert
    fun addEpisodes(episodes: List<EpisodeEntity>)

}