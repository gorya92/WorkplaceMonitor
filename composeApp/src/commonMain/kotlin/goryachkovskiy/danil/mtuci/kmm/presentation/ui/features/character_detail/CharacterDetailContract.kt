package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.character_detail

import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEffect
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiEvent
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.UiState

interface CharacterDetailContract {
    sealed interface Event : UiEvent {
        object OnFavoriteClick : Event
        object OnTryCheckAgainClick : Event
        object OnBackPressed : Event
    }

    data class State(
        val workplace: ResourceUiState<Workplace>,
        val isFavorite: ResourceUiState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect {
        object CharacterAdded : Effect
        object CharacterRemoved : Effect
        object BackNavigation : Effect
    }
}