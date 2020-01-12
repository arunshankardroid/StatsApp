package com.arun.statsapp.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Stat(@SerializedName("match_id") val matchId: String,
                @SerializedName("stat_type") val statType: String,
                @SerializedName("team_A") val teamA: Team,
                @SerializedName("team_B") val teamB: Team)

data class Team(@SerializedName("id") val id: Int,
                @SerializedName("name") val name: String,
                @SerializedName("code") val code: String,
                @SerializedName("short_name") val shortName: String,
                @SerializedName("top_players") val topPlayers: List<Player>)

data class Player(@SerializedName("id") val id: Int,
                  @SerializedName("position") val position: String,
                  @SerializedName("full_name") val fullName: String,
                  @SerializedName("short_name") val shortName: String,
                  @SerializedName("stat_value") val statValue: Int,
                  @SerializedName("jumper_number") val jumperNumber: Int,
                  var teamId: Int)

data class PlayerDetail(@SerializedName("full_name") val fullName: String,
                        @SerializedName("position") val position: String,
                        @SerializedName("last_match_stats") val lastMatchStats: JsonObject,
                        @SerializedName("id") val id: Int)
