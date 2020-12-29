package com.example.myapp.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapp.repository.local.entity.CharacterEntity
import com.example.myapp.repository.local.entity.EpisodeEntity

@Database(entities = [CharacterEntity::class, EpisodeEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun rickAndMortyDao(): RickAndMortyDao

}