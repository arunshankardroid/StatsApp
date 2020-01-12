package com.arun.statsapp.ui.main.viewmodel

import com.arun.statsapp.model.Stat

data class StatResponseUI(val isLoading: Boolean, val stats: List<Stat>, val isEmpty: Boolean = false, val isError: Boolean = false)



