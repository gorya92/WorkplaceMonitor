package goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.features.characters.CharactersScreen
import goryachkovskiy.danil.mtuci.kmm.presentation.ui.theme.blue
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import workplacemonitor.composeapp.generated.resources.Res
import workplacemonitor.composeapp.generated.resources.eye_password
import workplacemonitor.composeapp.generated.resources.logo

class AuthScreen    (
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val charactersViewModel = getScreenModel<AuthViewModel>()

        val state by charactersViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            charactersViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is AuthContract.Effect.NavigateToCharacters ->  navigator.push(CharactersScreen(effect.token))
                }
            }
        }

        Scaffold(
        ) { padding ->
            Login(
                onLoginClick = { login, pass ->
                    charactersViewModel.setEvent(AuthContract.Event.OnCharactersClick(login, pass))

                }
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Login(
          onLoginClick: (login: String, pass: String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp,),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(280.dp).padding(bottom = 60.dp),
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo"
        )
        Text(
            text = "Login",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp

            ),
            modifier = Modifier.padding(bottom = 40.dp),

            )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(bottom = 20.dp),
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { /* Handle next action */ })
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.padding(bottom = 20.dp),
            label = { Text(text = "Password") },
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {  }),
            trailingIcon = {
                val icon = if (passwordVisible) painterResource(Res.drawable.eye_password) else painterResource(Res.drawable.eye_password)
                IconButton(onClick = { passwordVisible = !passwordVisible }, modifier = Modifier) {
                    Icon(icon, contentDescription = "Toggle password visibility")
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {   onLoginClick(email, password) }, modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 12.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = blue, contentColor = Color.White)
        ) {
            Text(text = "Login",  style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp

            ),)
        }
    }
}

