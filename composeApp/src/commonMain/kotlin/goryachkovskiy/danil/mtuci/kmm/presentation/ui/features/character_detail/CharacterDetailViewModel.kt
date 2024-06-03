package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.character_detail

import cafe.adriel.voyager.core.model.coroutineScope
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharacterUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetSpecificWorkplacesUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.IsCharacterFavoriteUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.SwitchCharacterFavoriteUseCase
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.BaseViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val isCharacterFavoriteUseCase: IsCharacterFavoriteUseCase,
    private val switchCharacterFavoriteUseCase: SwitchCharacterFavoriteUseCase,
    private val getSpecificWorkplacesUseCase: GetSpecificWorkplacesUseCase,
    private val characterId: Int,
) : BaseViewModel<CharacterDetailContract.Event, CharacterDetailContract.State, CharacterDetailContract.Effect>() {

    init {
        getCharacter(characterId)
    }

    override fun createInitialState(): CharacterDetailContract.State =
        CharacterDetailContract.State(
            workplace = ResourceUiState.Idle,
            isFavorite = ResourceUiState.Idle,
        )

    override fun handleEvent(event: CharacterDetailContract.Event) {
        when (event) {
            CharacterDetailContract.Event.OnFavoriteClick -> switchCharacterFavorite(characterId)
            CharacterDetailContract.Event.OnTryCheckAgainClick -> getCharacter(characterId)
            CharacterDetailContract.Event.OnBackPressed -> setEffect { CharacterDetailContract.Effect.BackNavigation }
        }
    }

    private fun getCharacter(characterId: Int) {
        setState { copy(workplace = ResourceUiState.Loading) }
        coroutineScope.launch {
            getSpecificWorkplacesUseCase(characterId)
                .onSuccess { setState { copy(workplace = ResourceUiState.Success(it)) } }
                .onFailure { setState { copy(workplace = ResourceUiState.Error()) } }
        }
    }

    private fun checkIfIsFavorite(idCharacter: Int) {
        setState { copy(isFavorite = ResourceUiState.Loading) }
        coroutineScope.launch {
            isCharacterFavoriteUseCase(idCharacter)
                .onSuccess { setState { copy(isFavorite = ResourceUiState.Success(it)) } }
                .onFailure { setState { copy(isFavorite = ResourceUiState.Error()) } }
        }
    }

    private fun switchCharacterFavorite(idCharacter: Int) {
        setState { copy(isFavorite = ResourceUiState.Loading) }
        coroutineScope.launch {
            switchCharacterFavoriteUseCase(idCharacter)
                .onSuccess {
                    setState { copy(isFavorite = ResourceUiState.Success(it)) }
                    setEffect { CharacterDetailContract.Effect.CharacterAdded }
                }.onFailure { setState { copy(isFavorite = ResourceUiState.Error()) } }
        }
    }
}