package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters_favorites

import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEffect
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEvent
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiState

interface CharactersFavoritesContract {
    sealed interface Event : UiEvent {
        object OnBackPressed : Event
        object OnTryCheckAgainClick : Event
        data class OnCharacterClick(val idCharacter: Int) : Event
    }

    data class State(
        val charactersFavorites: ResourceUiState<List<Character>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailCharacter(val idCharacter: Int) : Effect
        object BackNavigation : Effect

    }
}