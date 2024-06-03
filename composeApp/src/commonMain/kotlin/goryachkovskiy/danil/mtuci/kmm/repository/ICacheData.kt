package goryachkovskiy.danil.mtuci.kmm.repository

import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface ICacheData {
    suspend fun addCharacterToFavorite(character: Character)
    suspend fun removeCharacterFromFavorite(idCharacter: Int)
    suspend fun getAllCharacterFavorites(): Flow<List<Character>>
    suspend fun removeToken()
    suspend fun addToken(string: String)
    suspend fun checkToken():String
    suspend fun isCharacterFavorite(idCharacter: Int): Boolean
}