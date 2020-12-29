package com.example.myapp.interactor

import com.example.myapp.domain.Character
import com.example.myapp.repository.RickAndMortyRepository
import io.reactivex.Single
import javax.inject.Inject

class RickAndMortyInteractor @Inject constructor(
    private val repo: RickAndMortyRepository
) {

    fun getCharacters(page: Int): Single<List<Character>> {
        return repo.getCharacters(page)
    }

    fun getCharactersFromApi(page: Int): Single<List<Character>> {
        return repo.getCharactersFromApi(page)
    }

}