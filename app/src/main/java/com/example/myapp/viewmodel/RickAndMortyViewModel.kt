package com.example.myapp.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapp.domain.Character
import com.example.myapp.interactor.RickAndMortyInteractor
import io.reactivex.disposables.Disposable

class RickAndMortyViewModel @ViewModelInject constructor(
    val app: Application,
    private val interactor: RickAndMortyInteractor,
) : AndroidViewModel(app) {

    private var disposable: Disposable? = null

    val characters = MutableLiveData<List<Character>>()
    private val page = MutableLiveData(1)

    fun getCharacters() {
        val param = page.value ?: 1
        disposable = interactor.getCharacters(param).subscribe { charList, error ->
            if (error == null && charList != null) {
                characters.value = charList
            }
        }
    }

    fun loadFromApi() {
        val param = page.value ?: 1
        disposable = interactor.getCharactersFromApi(param).subscribe { res, error ->
            if (error == null && res != null) {
                characters.value = res
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}