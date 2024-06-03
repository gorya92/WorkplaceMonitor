package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.ActionBarIcon
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.WorkplaceList
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.state.ManagementResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth.AuthScreen
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.character_detail.CharacterDetailScreen
import kotlinx.coroutines.flow.collectLatest

class CharactersScreen(token: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val charactersViewModel = getScreenModel<WorkplacesViewModel>()

        val state by charactersViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            charactersViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is WorkplacesContract.Effect.NavigateToDetailCharacter ->
                        navigator.push(CharacterDetailScreen(effect.title,effect.idCharacter))

                    WorkplacesContract.Effect.NavigateToAuth -> navigator.push(AuthScreen())
                }
            }
        }

        Scaffold(
            topBar = { ActionAppBar { charactersViewModel.setEvent(WorkplacesContract.Event.OnExit) } }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.workplace,
                successView = { characters ->
                    WorkplaceList(
                        characters = characters,
                        onCharacterClick = {title ,idCharacter ->
                            charactersViewModel.setEvent(
                                WorkplacesContract.Event.OnCharacterClick(
                                    title,
                                    idCharacter
                                )
                            )
                        }
                    )
                },
                onTryAgain = { charactersViewModel.setEvent(WorkplacesContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { charactersViewModel.setEvent(WorkplacesContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
}

@Composable
fun ActionAppBar(
    onClickFavorite: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Workspace Monitor") },
        actions = {
            ActionBarIcon(
                onClick = onClickFavorite,
                icon = Icons.Filled.ExitToApp
            )
        }
    )
}


