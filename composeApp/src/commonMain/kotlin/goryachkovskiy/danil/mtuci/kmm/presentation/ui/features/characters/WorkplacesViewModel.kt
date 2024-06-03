package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters

import cafe.adriel.voyager.core.model.coroutineScope
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.DeleteTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetTokenUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.GetWorkplacesUseCase
import goryachkovskiy.danil.mtuci.kmm.presentation.mvi.BaseViewModel
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class WorkplacesViewModel(
    private val getWorkplacesUseCase: GetWorkplacesUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase

    ) : BaseViewModel<WorkplacesContract.Event, WorkplacesContract.State, WorkplacesContract.Effect>() {

    init {
        getWorkplaces()
    }

    override fun createInitialState(): WorkplacesContract.State =
        WorkplacesContract.State(workplace = ResourceUiState.Idle)

    override fun handleEvent(event: WorkplacesContract.Event) {
        when (event) {
            WorkplacesContract.Event.OnTryCheckAgainClick -> getWorkplaces()
            is WorkplacesContract.Event.OnCharacterClick -> setEffect {
                WorkplacesContract.Effect.NavigateToDetailCharacter(
                    event.title,
                    event.idCharacter
                )
            }

            WorkplacesContract.Event.OnExit -> remove()
        }
    }

    private fun remove() {
        coroutineScope.launch {
            deleteTokenUseCase( Unit).onSuccess {
                    setEffect { WorkplacesContract.Effect.NavigateToAuth }
            }
                .onFailure {
                    setEffect { WorkplacesContract.Effect.NavigateToAuth }

                }
        }
    }

    private fun getWorkplaces() {

        setState { copy(workplace = ResourceUiState.Loading) }
        coroutineScope.launch {
            getTokenUseCase(Unit).onSuccess {
                if (it == "") {

                } else {
                    getWorkplacesUseCase(it)
                        .onSuccess {
                            setState {
                                copy(
                                    workplace = if (it.isEmpty())
                                        ResourceUiState.Empty
                                    else
                                        ResourceUiState.Success(it)
                                )
                            }
                        }
                }
            }
                .onFailure {
                    setState {
                        copy(workplace = ResourceUiState.Error())
                    }

                }
        }
    }
}

