package com.example.kotlinprac.mapper.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinprac.mapper.CharacterModel
import com.example.kotlinprac.mapper.data.repository.CharactersRepo
import com.example.kotlinprac.mapper.data.responses.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchCharactersViewModel @Inject constructor(
    private val charactersRepo: CharactersRepo
): ViewModel() {

    private val TAG = this.javaClass.simpleName

    // var로 MutableLiveData()를 만들지 않고 val로 만들어서 커스텀 게터를 통해 값을 넘긴다. 이 방법의 장점?
    private val _fetchCharacters = MutableLiveData<CharacterModel>()
    val fetchCharacters: LiveData<CharacterModel> get() = _fetchCharacters

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String> get() = _errorResponse

    fun getCharacters() {
        viewModelScope.launch {
            val charactersResponse = charactersRepo.getCharacters()
            Log.e(TAG, charactersResponse.toString())
            when (charactersResponse) {
                is ApiResource.Success -> {
                    _fetchCharacters.postValue(charactersResponse.value)
                }
                is ApiResource.Error -> {
                    _errorResponse.postValue(charactersResponse.errorBody.toString())
                }
                ApiResource.Loading -> {}
            }
        }
    }

}