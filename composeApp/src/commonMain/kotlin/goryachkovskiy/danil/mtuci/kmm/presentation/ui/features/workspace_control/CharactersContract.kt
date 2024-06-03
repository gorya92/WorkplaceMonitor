package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.workspace_control

import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEffect
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEvent
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiState

interface CharactersContract {
    sealed interface Event : UiEvent {
        object OnTryCheckAgainClick : Event
        object OnFavoritesClick : Event
        data class OnCharacterClick(val idCharacter: Int) : Event
    }

    data class State(
        val characters: ResourceUiState<List<Character>>,
        val workplace: ResourceUiState<List<Workplace>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailCharacter(val idCharacter: Int) : Effect
        object NavigateToFavorites : Effect
    }
}


