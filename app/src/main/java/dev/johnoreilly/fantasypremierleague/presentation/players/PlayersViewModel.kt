package dev.johnoreilly.fantasypremierleague.presentation.players

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import dev.johnoreilly.common.data.repository.FantasyPremierLeagueRepository
import dev.johnoreilly.common.domain.entities.GameFixture
import dev.johnoreilly.common.domain.entities.Player
import kotlinx.coroutines.launch


class PlayersViewModel(
    private val repository: FantasyPremierLeagueRepository,
    private val logger: Kermit
) : ViewModel() {
    val pastFixtures = MutableLiveData<List<GameFixture>>(emptyList())
    val players = MutableLiveData<List<Player>>()
    val query = mutableStateOf("")

    init {
        viewModelScope.launch {
            pastFixtures.value = repository.getPastFixtures()
            players.value = repository.getPlayers()
        }
    }

    fun onPlayerSearchQueryChange(query: String) {
        viewModelScope.launch {
            players.value = repository
                .getPlayers()
                .filter { player -> player.name.toLowerCase().contains(query.toLowerCase()) }
        }
    }
}