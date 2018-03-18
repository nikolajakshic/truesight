package com.nikola.jakshic.dagger.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nikola.jakshic.dagger.model.match.Match;
import com.nikola.jakshic.dagger.repository.MatchRepository;

import java.util.List;

import javax.inject.Inject;

public class MatchViewModel extends ViewModel {

    private MutableLiveData<List<Match>> list;
    private MatchRepository repository;
    private MutableLiveData<Boolean> loading;
    private boolean initialFetch;

    @Inject
    public MatchViewModel(MatchRepository repository) {
        this.repository = repository;
        list = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        loading.setValue(false);
    }

    public void initialFetch(long id) {
        if (!initialFetch) {
            repository.fetchMatches(list, loading, id);
            initialFetch = true;
        }
    }

    public void fetchHeroes(long id) {
        repository.fetchMatches(list, loading, id);
    }

    public MutableLiveData<List<Match>> getMatches() {
        return list;
    }

    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }
}