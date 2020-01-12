package com.arun.statsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arun.statsapp.R
import com.arun.statsapp.di.StatRepoInjection
import com.arun.statsapp.events.RxEventBus
import com.arun.statsapp.model.Stat
import com.arun.statsapp.ui.main.viewmodel.StatResponseUI
import com.arun.statsapp.ui.main.viewmodel.StatViewModel
import com.arun.statsapp.ui.main.viewmodel.StatViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: StatsAdapter
    lateinit var viewModel: StatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupPullToRefresh()
        setupEventListener()
    }

    private fun setupEventListener() {
        CompositeDisposable().add(RxEventBus.listenTo(PlayerClicked::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{playerClicked ->
            activity?.let {
                val intent = Intent(it, PlayerDetailsActivity::class.java)
                intent.putExtra("team_id", playerClicked.player.teamId)
                intent.putExtra("player_id", playerClicked.player.id)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStats()
    }

    private fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, StatViewModelFactory(StatRepoInjection.provideStatRepository())).get(StatViewModel::class.java)
        viewModel.response.observe(this, responseObserver)
    }

    private fun setupPullToRefresh() {
        context?.let {
            swipe_refresh?.apply {
                setColorSchemeColors(ContextCompat.getColor(it, R.color.colorPrimary))
                setOnRefreshListener {
                    viewModel.getStats()
                }
            }
        }
    }

    private val responseObserver = Observer<StatResponseUI> {
        swipe_refresh?.isRefreshing = false
        setupRecyclerView(it.stats)
        empty_text.showView(it.isEmpty)
        error_text.showView(it.isError)
        progressBar.showView(it.isLoading)
    }

    private fun setupRecyclerView(stats: List<Stat>) {
        val statItems = mutableListOf<StatItem>()
        stats.forEach {
            statItems.add(StatItem(StatsAdapter.ViewType.STAT_TYPE, it.statType))
            statItems.add(StatItem(StatsAdapter.ViewType.PLAYER, it))
        }
        adapter = StatsAdapter(statItems)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        val itemDecor = DividerItemDecoration(activity, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecor)
    }

}
