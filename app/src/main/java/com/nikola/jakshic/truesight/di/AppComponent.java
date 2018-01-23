package com.nikola.jakshic.truesight.di;

import com.nikola.jakshic.truesight.view.activity.SearchActivity;
import com.nikola.jakshic.truesight.view.fragment.HeroFragment;
import com.nikola.jakshic.truesight.view.fragment.MatchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(SearchActivity activity);

    void inject(MatchFragment fragment);

    void inject(HeroFragment fragment);
}
