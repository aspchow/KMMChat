package com.avinash.mykmmchat.di

import com.avinash.database.db.AppDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        val driver = NativeSqliteDriver(AppDatabase.Schema, "Appdatabase.db")
        AppDatabase(driver)
    }
}