package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters

import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEffect
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEvent
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiState

interface WorkplacesContract {
    sealed interface Event : UiEvent {
        object OnTryCheckAgainClick : Event
        data class OnCharacterClick(val title: String,val idCharacter: Int) : Event

        object OnExit: Event
    }

    data class State(
        val workplace: ResourceUiState<List<Workplace>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailCharacter(val title: String,val idCharacter: Int) : Effect

        object NavigateToAuth : Effect

    }
}


