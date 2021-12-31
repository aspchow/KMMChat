package com.avinash.mykmmchat.android.di

import com.avinash.mykmmchat.android.AppViewModel
import com.avinash.mykmmchat.repository.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {
    viewModel {
        AppViewModel(Repository())
    }
}