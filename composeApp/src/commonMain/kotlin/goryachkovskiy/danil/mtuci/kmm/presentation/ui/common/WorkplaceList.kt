package goryachkovskiy.danil.mtuci.kmm.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.common.WorkplaceItem

@Composable
fun WorkplaceList(
    characters: List<Workplace>,
    onCharacterClick: (String,Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(characters) { character ->
            WorkplaceItem(
                character = character,
                onClick = { onCharacterClick(character.title, character.id) }
            )
        }
    }
}