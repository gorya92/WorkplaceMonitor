package goryachkovskiy.danil.mtuci.kmm.domain

import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getCharacters(): List<Character>
    suspend fun auth(login:String, password:String): String
    suspend fun getCharactersFavorites(): Flow<List<Character>>
    suspend fun getToken(): String
    suspend fun addToken(string: String)
    suspend fun removeToken()
    suspend fun getCharacter(id: Int): Character
    suspend fun addCharacterToFavorites(character: Character)
    suspend fun removeCharacterFromFavorite(idCharacter: Int)
    suspend fun isCharacterFavorite(idCharacter: Int): Boolean

    suspend fun setTokenFromApi(token: String)
    suspend fun getSpecificWorkplacesFromApi(id: Int): Workplace
    suspend fun getWorkplacesFromApi(token: String): List<Workplace>
}