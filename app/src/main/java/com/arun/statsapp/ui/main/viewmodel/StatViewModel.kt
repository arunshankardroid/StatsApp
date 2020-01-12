package com.arun.statsapp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arun.statsapp.model.StatDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StatViewModel(private val repository: StatDataSource) : ViewModel() {

    val _response = MutableLiveData<StatResponseUI>()
    val response: LiveData<StatResponseUI> = _response

    fun getStats() {
        _response.postValue(
            StatResponseUI(
                true,
                emptyList()
            )
        )
        CompositeDisposable().add(
            repository.retrieveStats()
                .observeOn(Schedulers.io())
                .doOnSubscribe { }
                .subscribe({
                    if(it.isEmpty()) {
                        _response.postValue(
                            StatResponseUI(
                                false,
                                emptyList(),
                                isEmpty = true
                            )
                        )
                    } else {
                        it.forEach {stat ->
                            stat.teamA.topPlayers.forEach {
                                it.teamId = stat.teamA.id
                            }
                            stat.teamB.topPlayers.forEach {
                                it.teamId = stat.teamB.id
                            }
                        }
                        _response.postValue(
                            StatResponseUI(
                                false,
                                it
                            )
                        )
                    }
                }, {
                    _response.postValue(
                        StatResponseUI(
                            false,
                            emptyList(),
                            isError = true
                        )
                    )
                })

        )
    }


}
