package com.example.myapp.repository

import com.example.myapp.domain.Character
import com.example.myapp.domain.Location
import com.example.myapp.repository.local.database.RickAndMortyDao
import com.example.myapp.repository.local.entity.CharacterEntity
import com.example.myapp.repository.local.entity.EpisodeEntity
import com.example.myapp.repository.network.service.RickAndMortyService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.example.myapp.repository.local.entity.Location as LocationEntity

class RickAndMortyRepository @Inject constructor(
    private val retrofitService: RickAndMortyService,
    private val dao: RickAndMortyDao
) {

    private val pageSize = 20

    fun getCharacters(page: Int): Single<List<Character>> {
        return getLocalCharacteres(page)
    }

    private fun getLocalCharacteres(page: Int): Single<List<Character>> {
        return dao.getCharacters()
            .map { entidades ->
                val arr = mutableListOf<Character>()
                entidades.map { entity ->
                    arr.add(
                        Character(
                            entity.character.id,
                            entity.character.name,
                            entity.character.status,
                            entity.character.species,
                            entity.character.type,
                            entity.character.gender,
                            Location(entity.character.origin.name, entity.character.origin.url),
                            entity.character.image,
                            entity.episodes.map { ep -> ep.url },
                            entity.character.url
                        )
                    )
                }
                arr.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCharactersFromApi(page: Int): Single<List<Character>> {
        return retrofitService.characterList(page)
            .map {
                val arr = mutableListOf<Character>()
                it.results.forEach { dto ->
                    val character = CharacterEntity(
                        dto.id,
                        dto.name,
                        dto.status,
                        dto.species,
                        dto.type,
                        dto.gender,
                        LocationEntity(dto.origin.name, dto.origin.url),
                        dto.image,
                        dto.url
                    )
                    dao.addCharacter(character)

                    val episodes = mutableListOf<EpisodeEntity>()
                    dto.episode.forEach { url ->
                        episodes.add(EpisodeEntity(characterId = dto.id, url = url))
                    }
                    dao.addEpisodes(episodes)

                    arr.add(
                        Character(
                            dto.id,
                            dto.name,
                            dto.status,
                            dto.species,
                            dto.type,
                            dto.gender,
                            Location(dto.origin.name, dto.origin.url),
                            dto.image,
                            dto.episode,
                            dto.url
                        )
                    )
                }
                arr.toList()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}