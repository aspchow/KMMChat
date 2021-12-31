package com.avinash.mykmmchat.di

import com.avinash.database.db.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        AppDatabase(AndroidSqliteDriver(AppDatabase.Schema, get(), "Appdatabase.db"))
    }

}