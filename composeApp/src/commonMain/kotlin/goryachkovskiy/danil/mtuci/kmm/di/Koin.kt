package goryachkovskiy.danil.mtuci.kmm.di

import goryachkovskiy.danil.mtuci.kmm.data_cache.CacheDataImp
import goryachkovskiy.danil.mtuci.kmm.data_cache.sqldelight.SharedDatabase
import goryachkovskiy.danil.mtuci.kmm.data_remote.RemoteDataImp
import goryachkovskiy.danil.mtuci.kmm.data_remote.model.mapper.ApiCharacterMapper
import goryachkovskiy.danil.mtuci.kmm.data_remote.model.mapper.ApiWorkplaceMapper
import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.DeleteTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharacterUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharactersFavoritesUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharactersUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetLoginUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetSpecificWorkplacesUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetWorkplacesUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.IsCharacterFavoriteUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.SetTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth.AuthViewModel
import goryachkovskiy.danil.mtuci.kmm.repository.ICacheData
import goryachkovskiy.danil.mtuci.kmm.repository.IRemoteData
import goryachkovskiy.danil.mtuci.kmm.repository.RepositoryImp
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.character_detail.CharacterDetailViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters.CharactersViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters.WorkplacesViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters_favorites.CharactersFavoritesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }

val viewModelModule = module {
    factory { CharactersViewModel(get()) }
    factory {  AuthViewModel(get(), get(),get(), get(), get()) }
    factory { CharactersFavoritesViewModel(get()) }
    factory { WorkplacesViewModel(get(), get(), get()) }
    factory { params -> CharacterDetailViewModel(get(), get(), get(),get(), params.get()) }
}


val useCasesModule: Module = module {
    factory { GetCharactersUseCase(get(), get()) }
    factory { GetCharactersFavoritesUseCase(get(), get()) }
    factory { GetTokenUseCase(get(), get()) }
    factory { GetSpecificWorkplacesUseCase(get(), get()) }
    factory { GetWorkplacesUseCase(get(), get()) }
    factory { SetTokenUseCase(get(), get()) }
    factory { DeleteTokenUseCase(get(), get()) }
    factory { GetCharacterUseCase(get(), get()) }
    factory { GetLoginUseCase(get(), get()) }
    factory { IsCharacterFavoriteUseCase(get(), get()) }
    factory { SwitchCharacterFavoriteUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }


}

val ktorModule = module {
    single {
        HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "https://rickandmortyapi.com" }
}

val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val mapperModule = module {
    factory { ApiCharacterMapper() }
    factory { ApiWorkplaceMapper() }
}

fun initKoin() = initKoin {}



