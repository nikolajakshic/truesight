package com.nikola.jakshic.dagger.ui.matchstats

import androidx.lifecycle.LiveData
import com.nikola.jakshic.dagger.repository.MatchRepository
import com.nikola.jakshic.dagger.ui.ScopedViewModel
import com.nikola.jakshic.dagger.vo.Stats
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class MatchStatsViewModel @Inject constructor(
        private val repository: MatchRepository) : ScopedViewModel() {

    lateinit var match: LiveData<Stats>
        private set

    private var initialFetch = false

    fun initialFetch(id: Long) {
        if (!initialFetch) {
            initialFetch = true
            match = repository.getMatchStatsLiveData(id)
            fetchMatchStats(id)
        }
    }

    fun fetchMatchStats(id: Long) {
        launch {
            repository.fetchMatchStats(id)
        }
    }
}