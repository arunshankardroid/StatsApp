package com.arun.statsapp.data

import com.arun.statsapp.model.Player
import com.arun.statsapp.model.PlayerDetail
import com.arun.statsapp.model.Stat
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("matches/NRL20190101/topplayerstats.json;type=fantasy_points;type=tackles;type=runs;type=run_metres?limit=5&userkey=A00239D3-45F6-4A0A-810C-54A347F144C2")
    fun getStats(): Observable<List<Stat>>

    @GET("series/1/seasons/117/teams/{team_id}/players/{player_id}/detailedstats.json?&userkey=9024ec15-d791-4bfd-aa3b-5bcf5d36da4f")
    fun getPlayerDetails(@Path("team_id") teamId: Int, @Path("player_id") playerId: Int) : Observable<PlayerDetail>
}