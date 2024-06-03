package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth

import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEffect
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEvent
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiState

interface AuthContract {
    sealed interface Event : UiEvent {
        object OnTryCheckAgainClick : Event
        data class OnCharactersClick(val login:String,val password : String) : Event

    }

    data class State(
        val token: ResourceUiState<String>
    ) : UiState

    sealed interface Effect : UiEffect {

        data class NavigateToCharacters(val token: String): Effect
    }
}


