package goryachkovskiy.danil.mtuci.kmm.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {

        Text(
            text = character.name,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

