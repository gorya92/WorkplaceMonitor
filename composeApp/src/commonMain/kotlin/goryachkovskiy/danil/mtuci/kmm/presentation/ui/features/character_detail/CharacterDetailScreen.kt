package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.character_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberImagePainter
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.ArrowBackIcon
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.state.ManagementResourceUiState
import goryachkovskiy.danil.mtuci.kmm.presentation.model.ResourceUiState

import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.parameter.parametersOf

class CharacterDetailScreen(
    private val title: String,
    private val characterId: Int,
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val characterDetailViewModel =
            getScreenModel<CharacterDetailViewModel> { parametersOf(characterId) }

        val state by characterDetailViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            characterDetailViewModel.effect.collectLatest { effect ->
                when (effect) {
                    CharacterDetailContract.Effect.CharacterAdded ->
                        scaffoldState.snackbarHostState.showSnackbar("Character added to favorites")

                    CharacterDetailContract.Effect.CharacterRemoved ->
                        scaffoldState.snackbarHostState.showSnackbar("Character removed from favorites")

                    CharacterDetailContract.Effect.BackNavigation -> navigator.pop()
                }
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ActionBar(
                    character = state.workplace,
                    favorite = state.isFavorite,
                    onActionFavorite = { characterDetailViewModel.setEvent(CharacterDetailContract.Event.OnFavoriteClick) },
                    onBackPressed = { characterDetailViewModel.setEvent(CharacterDetailContract.Event.OnBackPressed) }
                )
            }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.workplace,
                successView = { character ->
                    CharacterDetail(character)
                },
                onTryAgain = { characterDetailViewModel.setEvent(CharacterDetailContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { characterDetailViewModel.setEvent(CharacterDetailContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CharacterDetail(character: Workplace) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val painter = rememberImagePainter("http://192.168.0.245:8000/static-ready/workplace${character.id}.jpg")
        Image(
            painter = painter,
            contentDescription = "image",
        )
        Text(
            text = "Количество людей: ${character.current_people} ",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = "Необходимое количество:  ${character.max_people}",
            style = MaterialTheme.typography.h6
        )

    }
}

@Composable
fun ActionBar(
    character: ResourceUiState<Workplace>,
    favorite: ResourceUiState<Boolean>,
    onActionFavorite: () -> Unit,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {
            ManagementResourceUiState(
                resourceUiState = character,
                successView = { Text(text = it.title) },
                loadingView = { Text(text = "....") },
                onCheckAgain = {},
                onTryAgain = {}
            )
        },
        navigationIcon = { ArrowBackIcon(onBackPressed) },

    )
}

