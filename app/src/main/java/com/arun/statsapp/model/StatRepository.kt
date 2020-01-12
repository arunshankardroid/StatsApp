package com.arun.statsapp.model

import com.arun.statsapp.data.ApiClient
import io.reactivex.Observable

class StatRepository : StatDataSource {

    override fun retrieveStats(): Observable<List<Stat>> {
        return ApiClient.build().getStats()
    }

    override fun retrieveDetails(teamId: Int, playerId: Int): Observable<PlayerDetail> {
        return ApiClient.build().getPlayerDetails(teamId, playerId)
    }

}