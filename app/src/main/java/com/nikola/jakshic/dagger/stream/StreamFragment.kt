package com.nikola.jakshic.dagger.stream

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.jakshic.dagger.HomeActivity
import com.nikola.jakshic.dagger.R
import com.nikola.jakshic.dagger.common.Status
import com.nikola.jakshic.dagger.common.hasNetworkConnection
import com.nikola.jakshic.dagger.common.inflate
import com.nikola.jakshic.dagger.common.toast
import com.nikola.jakshic.dagger.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stream.*

@AndroidEntryPoint
class StreamFragment : Fragment(), HomeActivity.OnNavigationItemReselectedListener {
    private val viewModel: StreamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_stream)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.inflateMenu(R.menu.menu_home)

        val adapter = StreamAdapter {
            val intent = Intent(context, StreamPlayerActivity::class.java)
            intent.putExtra("user-name", it)
            startActivity(intent)
        }

        recView.layoutManager = LinearLayoutManager(context)
        recView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recView.adapter = adapter
        recView.setHasFixedSize(true)

        viewModel.initialFetch()
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    tvNetworkError.visibility = View.GONE
                    swipeRefresh.isRefreshing = true
                }
                Status.ERROR -> {
                    swipeRefresh.isRefreshing = false
                    tvNetworkError.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    tvNetworkError.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                }
                else -> {
                    tvNetworkError.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                }
            }
        }
        viewModel.streams.observe(viewLifecycleOwner, Observer(adapter::addData))

        swipeRefresh.setOnRefreshListener {
            if (hasNetworkConnection())
                viewModel.getStreams()
            else {
                toast(getString(R.string.error_network_connection))
                swipeRefresh.isRefreshing = false
            }
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_home_search -> {
                    startActivity(Intent(activity, SearchActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onItemReselected() {
        recView.smoothScrollToPosition(0)
    }
}