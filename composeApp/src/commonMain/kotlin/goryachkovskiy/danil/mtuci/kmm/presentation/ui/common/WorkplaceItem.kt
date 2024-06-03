package goryachkovskiy.danil.mtuci.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.theme.grey

@Composable
fun WorkplaceItem(
    character: Workplace,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
            .padding(12.dp)
            .background(grey, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(60.dp)

    ) {
        Text(
            text = character.title,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = "${character.current_people}/${character.max_people}",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(start = 12.dp, top = 2.dp)
        )
        if (character.current_people>= character.max_people){
            ColoredCircle(Color.Green,  modifier = Modifier.weight(1f))
        }
        else {
            ColoredCircle(Color.Red,  modifier = Modifier.fillMaxWidth())
        }

    }
}

@Composable
fun ColoredCircle(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(end = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterEnd)
                .background(color = color, shape = RoundedCornerShape(100.dp))
        )
    }
}

