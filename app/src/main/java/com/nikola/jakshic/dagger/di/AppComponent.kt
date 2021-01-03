package com.nikola.jakshic.dagger.di

import com.nikola.jakshic.dagger.DaggerApp
import com.nikola.jakshic.dagger.bookmark.match.MatchBookmarkFragment
import com.nikola.jakshic.dagger.bookmark.player.PlayerBookmarkFragment
import com.nikola.jakshic.dagger.competitive.CompetitiveFragment
import com.nikola.jakshic.dagger.leaderboard.RegionFragment
import com.nikola.jakshic.dagger.matchstats.MatchStatsFragment
import com.nikola.jakshic.dagger.profile.ProfileFragment
import com.nikola.jakshic.dagger.profile.heroes.HeroFragment
import com.nikola.jakshic.dagger.profile.matches.MatchFragment
import com.nikola.jakshic.dagger.profile.matches.byhero.MatchesByHeroFragment
import com.nikola.jakshic.dagger.profile.peers.PeerFragment
import com.nikola.jakshic.dagger.search.SearchFragment
import com.nikola.jakshic.dagger.stream.StreamFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DbModule::class), (ViewModelModule::class), (CoilModule::class)])
interface AppComponent {
    fun inject(application: DaggerApp)

    fun inject(fragment: SearchFragment)

    fun inject(fragment: MatchFragment)

    fun inject(fragment: HeroFragment)

    fun inject(fragment: MatchStatsFragment)

    fun inject(fragment: PlayerBookmarkFragment)

    fun inject(fragment: MatchBookmarkFragment)

    fun inject(fragment: PeerFragment)

    fun inject(fragment: CompetitiveFragment)

    fun inject(fragment: RegionFragment)

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: MatchesByHeroFragment)

    fun inject(fragment: StreamFragment)
}