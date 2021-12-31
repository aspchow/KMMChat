package com.avinash.mykmmchat.di

import com.avinash.database.db.AppDatabase
import com.avinash.mykmmchat.model.modelmapper.Mapper
import com.avinash.mykmmchat.networking.ApiUtility
import com.avinash.mykmmchat.repository.LocalRepository
import com.avinash.mykmmchat.repository.RemoteRepository
import com.avinash.mykmmchat.repository.Repository
import org.koin.dsl.module

fun repoModule() = module {
    single {
        Repository()
    }

    single {
        LocalRepository(get(), get())
    }

    single {
        RemoteRepository(get())
    }

    single {
        ApiUtility()
    }

    single {
        val database: AppDatabase = get()
        database.messageQueries
    }

    single {
        val database: AppDatabase = get()
        database.postQueries
    }

    single {
        Mapper()
    }
}