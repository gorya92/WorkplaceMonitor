package goryachkovskiy.danil.mtuci.kmm.data_cache.sqldelight

import app.cash.sqldelight.db.SqlDriver
import goryachkovskiy.danil.mtuci.kmm.data_cache.sqldelight.SharedDatabase

expect class DatabaseDriverFactory {
    suspend fun createDriver(): SqlDriver
}

interface AppModule {
    fun provideExampleDataSource(): SharedDatabase
}