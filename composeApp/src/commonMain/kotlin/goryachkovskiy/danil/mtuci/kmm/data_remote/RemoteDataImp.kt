package goryachkovskiy.danil.mtuci.kmm.data_remote

import goryachkovskiy.danil.mtuci.kmm.data_remote.model.ApiCharacter
import goryachkovskiy.danil.mtuci.kmm.data_remote.model.ApiCharactersResponse
import goryachkovskiy.danil.mtuci.kmm.data_remote.model.mapper.ApiCharacterMapper
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.domain.model.WorkplaceResponse
import goryachkovskiy.danil.mtuci.kmm.repository.IRemoteData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import kotlinx.serialization.json.Json

class RemoteDataImp(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val apiCharacterMapper: ApiCharacterMapper,
) : IRemoteData {
    override suspend fun getCharactersFromApi(): List<Character> =
        apiCharacterMapper.map(
            (httpClient.get("$endPoint/api/character").body<ApiCharactersResponse>()).results
        )

    override suspend fun getCharacterFromApi(id: Int): Character =
        apiCharacterMapper.map(
            httpClient.get("$endPoint/api/character/$id").body<ApiCharacter>()
        )

    override suspend  fun authFromApi(login: String, password: String): String {
        println("VIEWs" + login + password)
        val httpClient = HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Request: $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
        // Building the request
        val response: HttpResponse = httpClient.post("http://192.168.0.245:8000/auth/login") {
            setBody(FormDataContent(Parameters.build {
                append("username", login)
                append("password", password)
            }))
        }

        val responseBody = response.bodyAsText()
        println("Response body: $responseBody")
        // Parsing the response
        // Parsing the response directly

        // Регулярное выражение для поиска токена доступа
        val regex = Regex("\"access_token\":\"(.*?)\"")

// Поиск соответствия в строке JSON
        val matchResult = regex.find(responseBody)

        val accessToken: String = matchResult?.groupValues?.get(1) ?: ""
        println("Response body: $accessToken")

        // Returning the access token
        return accessToken

    }
    override suspend fun getWorkplacesFromApi(token: String): List<Workplace> {
        println("Getting workplaces from API")

        // Создание HTTP-клиента
        val httpClient = HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Request: $message")
                    }
                }
                level = LogLevel.ALL // Уровень логирования можно изменить по вашему усмотрению
            }
        }

        // Построение запроса
        val response: HttpResponse = httpClient.get("http://192.168.0.245:8000/work/user/workplaces") {
            // Установка заголовка Authorization с токеном доступа
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        // Получение тела ответа в виде строки
        val responseBody = response.bodyAsText()
        println("Response body: $responseBody")
        val workplaces: List<Workplace> = WorkplacesParser.parse(responseBody)


        // Возвращение списка рабочих мест
        return workplaces
    }

    override suspend fun setTokenFromApi(token: String) {
        val httpClient = HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Token: $message")
                        println("Token: ${getToken()}")
                    }
                }
                level = LogLevel.ALL // Уровень логирования можно изменить по вашему усмотрению
            }
        }

        // Построение запроса
        httpClient.get("http://192.168.0.245:8000/report/token?token=${getToken()}") {
            // Установка заголовка Authorization с токеном доступа
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }


    override suspend fun getSpecificWorkplaceFromApi(id: Int): Workplace {
        println("Getting workplaces from API")

        val httpClient = HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Request: $message")
                    }
                }
                level = LogLevel.ALL // Уровень логирования можно изменить по вашему усмотрению
            }
        }

        // Построение запроса
        val url = "http://192.168.0.245:8000/work/specific/workplace?id=$id"
        val response: HttpResponse = httpClient.get(url)

        val responseBody = response.bodyAsText()
        println("Response body: $responseBody")
        val workplace: WorkplaceResponse = WorkplaceParser.parse(responseBody)
        println("Response body: $workplace")
        val first = workplace.workplace.get(0)
        println("Response body: $first")


        return first
    }

    object WorkplacesParser {
        private val json = Json { ignoreUnknownKeys = true }

        fun parse(responseBody: String): List<Workplace> {
            return json.decodeFromString(responseBody)
        }
    }

    object WorkplaceParser {
        private val json = Json { ignoreUnknownKeys = true }

        fun parse(responseBody: String): WorkplaceResponse {
            return json.decodeFromString(responseBody)
        }
    }


}

expect fun getToken(): String