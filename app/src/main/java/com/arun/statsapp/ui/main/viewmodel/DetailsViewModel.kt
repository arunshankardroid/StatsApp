package com.arun.statsapp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arun.statsapp.model.StatDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val repository: StatDataSource) : ViewModel() {

    val _response = MutableLiveData<DetailsResponseUI>()
    val response: LiveData<DetailsResponseUI> = _response

    fun getDetails(teamId: Int, playerId: Int) {
        _response.postValue(
            DetailsResponseUI(
                true
            )
        )
        CompositeDisposable().add(
            repository.retrieveDetails(teamId, playerId)
                .observeOn(Schedulers.io())
                .doOnSubscribe { }
                .subscribe({
                        _response.postValue(
                            DetailsResponseUI(
                                false,
                                it
                            )
                        )
                }, {
                    Log.e("Details" , it.localizedMessage)
                })

        )
    }


}
