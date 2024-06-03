package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth

import cafe.adriel.voyager.core.model.coroutineScope
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.AuthParams
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharactersUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetLoginUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.SetTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.BaseViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val getLoginUseCase: GetLoginUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val switchCharacterFavoriteUseCase: SwitchCharacterFavoriteUseCase,


    ) : BaseViewModel<AuthContract.Event, AuthContract.State, AuthContract.Effect>() {

    init {
        getToken()
    }

    override fun createInitialState(): AuthContract.State =
        AuthContract.State(token = ResourceUiState.Idle)

    override fun handleEvent(event: AuthContract.Event) {
        when (event) {

            AuthContract.Event.OnTryCheckAgainClick -> TODO()
            is AuthContract.Event.OnCharactersClick -> getLogin(event.login, event.password)

        }
    }

    private fun getLogin(login: String, password: String) {
        switchCharacterFavorite(20)
        val authParams = AuthParams(login, password)
        println("VIEW")
        println("VIEW" + authParams.toString())
        setState { copy(token = ResourceUiState.Loading) }
        coroutineScope.launch {
            getLoginUseCase(authParams)
                .onSuccess {token ->
                    if (token.isEmpty())
                    else {
                        println("tokenset" + token)

                        setTokenUseCase(token)
                            .onSuccess {
                                setEffect { AuthContract.Effect.NavigateToCharacters(token) }
                            }
                            .onFailure{println("tokenset" + it)}
                    }

                }
        }

    }

    private fun getToken() {
        coroutineScope.launch {
            getTokenUseCase( Unit).onSuccess {
                if (it==""){

                }
                else{
                    setEffect { AuthContract.Effect.NavigateToCharacters(it) }
                }
                println("tokenget" + it)
            }
                .onFailure {                 println("tokenget" + it)
                }


        }
    }

    private fun switchCharacterFavorite(idCharacter: Int) {
        coroutineScope.launch {
            switchCharacterFavoriteUseCase(idCharacter)
                .onSuccess {
                                   println("TESTSUC" )
                }.onFailure { println("TESTLOOSE" + it ) }
        }
    }

}
