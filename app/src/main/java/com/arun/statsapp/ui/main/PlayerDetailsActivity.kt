package com.arun.statsapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arun.statsapp.R
import com.arun.statsapp.di.StatRepoInjection
import com.arun.statsapp.ui.main.viewmodel.DetailsResponseUI
import com.arun.statsapp.ui.main.viewmodel.DetailsViewModel
import com.arun.statsapp.ui.main.viewmodel.DetailsViewModelFactory
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_player_details.*

class PlayerDetailsActivity : AppCompatActivity() {

    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        setupViewModels()
        viewModel.getDetails(intent.getIntExtra("team_id",-1),
            intent.getIntExtra("player_id",-1))
    }

    private fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, DetailsViewModelFactory(StatRepoInjection.provideStatRepository())).get(
            DetailsViewModel::class.java)
        viewModel.response.observe(this, responseObserver)
    }

    private val responseObserver = Observer<DetailsResponseUI> {
        progressBar.showView(it.isLoading)
        main.showView(!it.isLoading)
        it.details?.let {
            headShot.loadImage(
                getString(R.string.imageUrl, it.id.toString()),
                getString(R.string.fallbackImageUrl)
            )
            full_name.text = getString(R.string.name, it.fullName)
            position.text = getString(R.string.position, it.position)
            val gson =  GsonBuilder().setPrettyPrinting().create()
            stats.text = getString(R.string.last_match_stats,gson.toJson(it.lastMatchStats))
        }
    }
}
