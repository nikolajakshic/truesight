package com.nikola.jakshic.truesight.view.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nikola.jakshic.truesight.R;
import com.nikola.jakshic.truesight.PlayerViewModel;
import com.nikola.jakshic.truesight.TrueSightApp;
import com.nikola.jakshic.truesight.util.NetworkUtil;
import com.nikola.jakshic.truesight.view.adapter.PlayerAdapter;

import javax.inject.Inject;

public class SearchActivity extends AppCompatActivity {

    private static final String STATE_QUERY = "query-state";
    private static final String STATE_FOCUS = "searchview-focus";
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private PlayerViewModel viewModel;
    private SearchView mSearchView;
    private String mQuery;
    private boolean mFocus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((TrueSightApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_search);

        ProgressBar mProgressBar = findViewById(R.id.progress_search);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel.class);

        PlayerAdapter mAdapter = new PlayerAdapter(this);

        viewModel.getPlayers().observe(this, mAdapter::addData);
        RecyclerView recyclerView = findViewById(R.id.recview_player);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.setOnTouchListener((v, event) -> {
            mSearchView.clearFocus();
            return false;
        });

        viewModel.isLoading().observe(this, aBoolean -> {
            mProgressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search_search);
        searchItem.expandActionView();
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setQuery(mQuery, false);
        mSearchView.setQueryHint("Search Players");

        if (mFocus)
            mSearchView.requestFocus();
        else
            mSearchView.clearFocus();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return false;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.clearList();
                if (NetworkUtil.isActive(SearchActivity.this)) {
                    viewModel.fetchPlayers(query);
                } else {
                    Toast.makeText(SearchActivity.this, "Check network connection!", Toast.LENGTH_SHORT).show();
                }
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_QUERY, String.valueOf(mSearchView.getQuery()));
        outState.putBoolean(STATE_FOCUS, mSearchView.hasFocus());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mQuery = savedInstanceState.getString(STATE_QUERY);
        mFocus = savedInstanceState.getBoolean(STATE_FOCUS);
    }
}