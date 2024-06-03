package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.workspace_control

import cafe.adriel.voyager.core.model.coroutineScope
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetCharactersUseCase
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.BaseViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getWorkspaceUseCase: GetCharactersUseCase,
) : BaseViewModel<CharactersContract.Event, CharactersContract.State, CharactersContract.Effect>() {

    init {
        getWorkspace()
    }

    override fun createInitialState(): CharactersContract.State =
        CharactersContract.State(characters = ResourceUiState.Idle, workplace = ResourceUiState.Idle)

    override fun handleEvent(event: CharactersContract.Event) {
        when (event) {
            CharactersContract.Event.OnTryCheckAgainClick -> getWorkspace()
            is CharactersContract.Event.OnCharacterClick -> setEffect {
                CharactersContract.Effect.NavigateToDetailCharacter(
                    event.idCharacter
                )
            }

            CharactersContract.Event.OnFavoritesClick -> setEffect { CharactersContract.Effect.NavigateToFavorites }
        }
    }

    private fun getWorkspace() {
        setState { copy(characters = ResourceUiState.Loading) }
        coroutineScope.launch {
            getWorkspaceUseCase(Unit)
                .onSuccess {
                    setState {
                        copy(
                            characters = if (it.isEmpty())
                                ResourceUiState.Empty
                            else
                                ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(characters = ResourceUiState.Error()) } }
        }
    }
}
