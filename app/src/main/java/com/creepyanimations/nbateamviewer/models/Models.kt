package com.creepyanimations.nbateamviewer.models

class TeamsResponse{

    val teams: List<Team>? = null
}

class Team {

    val wins: Int = 0
    val losses: Int = 0
    val full_name: String? = null
    val id: Int = 0
    val players: List<Player>? = null
}

class Player {

    val id: Int = 0
    val first_name: String? = null
    val last_name: String? = null
    val position: String? = null
    val number: Int = 0
}

