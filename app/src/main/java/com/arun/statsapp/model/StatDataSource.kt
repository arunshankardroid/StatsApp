package com.arun.statsapp.model

import io.reactivex.Observable

interface StatDataSource {

    fun retrieveStats() : Observable<List<Stat>>
    fun retrieveDetails(teamId: Int, playerId: Int): Observable<PlayerDetail>
}