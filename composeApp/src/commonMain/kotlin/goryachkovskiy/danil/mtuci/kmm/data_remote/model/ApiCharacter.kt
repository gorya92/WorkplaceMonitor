package goryachkovskiy.danil.mtuci.kmm.data_remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCharactersResponse(
    val results: List<ApiCharacter>
)

@Serializable
data class ApiCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String
)
@Serializable
data class ApiWorkplaceResponse(
    val results: List<ApiWorkplace>
)
@Serializable
data class ApiWorkplace(
    val max_people: Int,
    val current_people: Int,
    val title: String,
    val camera_url: String,
    val green_zone_coordinates: List<String>,
    val red_zone_coordinates: List<String>,
    val face_detection: Boolean
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val access_token: String,
    @SerialName("token_type")
    val token_type: String
)



@Serializable
data class Location(
    val name: String
)
