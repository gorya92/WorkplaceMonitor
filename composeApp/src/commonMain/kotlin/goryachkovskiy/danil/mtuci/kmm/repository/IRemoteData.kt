package goryachkovskiy.danil.mtuci.kmm.repository

import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace

interface IRemoteData {
    suspend fun getCharactersFromApi(): List<Character>
    suspend fun getCharacterFromApi(id: Int): Character

    suspend fun authFromApi(login:String, password:String): String

    suspend fun getWorkplacesFromApi(token: String): List<Workplace>

    suspend fun setTokenFromApi(token: String)
    suspend fun getSpecificWorkplaceFromApi(id: Int): Workplace
}