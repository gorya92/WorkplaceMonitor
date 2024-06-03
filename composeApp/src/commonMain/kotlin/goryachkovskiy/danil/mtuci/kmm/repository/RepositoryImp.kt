package goryachkovskiy.danil.mtuci.kmm.repository

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.repository.ICacheData
import goryachkovskiy.danil.mtuci.kmm.repository.IRemoteData
import kotlinx.coroutines.flow.Flow

class RepositoryImp(
    private val cacheData: ICacheData,
    private val remoteData: IRemoteData,
) : IRepository {

    override suspend fun getCharacters(): List<Character> =
        remoteData.getCharactersFromApi()

    override suspend fun auth(login:String, password:String): String {
        println("VIEW" + login + password)

        val res = remoteData.authFromApi(login, password)
        println("VIEW" + res)
        return res
    }

    override suspend fun addToken(string: String) {
        println("addToken" + string)

        cacheData.addToken(string)
    }

    override suspend fun getToken(): String =
        cacheData.checkToken()

    override suspend fun removeToken() {
        cacheData.removeToken()
    }

    override suspend fun getCharactersFavorites(): Flow<List<Character>> =
        cacheData.getAllCharacterFavorites()

    override suspend fun getCharacter(id: Int): Character =
        remoteData.getCharacterFromApi(id)

    override suspend fun addCharacterToFavorites(character: Character) =
        cacheData.addCharacterToFavorite(character)

    override suspend fun removeCharacterFromFavorite(idCharacter: Int) =
        cacheData.removeCharacterFromFavorite(idCharacter)

    override suspend fun isCharacterFavorite(idCharacter: Int): Boolean =
        cacheData.isCharacterFavorite(idCharacter)

    override suspend fun getWorkplacesFromApi(token: String): List<Workplace> =
        remoteData.getWorkplacesFromApi(token)

    override suspend fun setTokenFromApi(token: String) =
        remoteData.setTokenFromApi(token)
    override suspend fun getSpecificWorkplacesFromApi(id: Int): Workplace =
        remoteData.getSpecificWorkplaceFromApi(id)
}