package goryachkovskiy.danil.mtuci.kmm.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val origin: String,
    val location: String,
    val image: String
)

@Serializable
data class Workplace(
    @SerialName("id") val id: Int,
    @SerialName("max_people") val max_people: Int,
    @SerialName("current_people") val current_people: Int,
    @SerialName("title") val title: String,
    @SerialName("camera_url") val camera_url: String,
    @SerialName("green_zone_coordinates") val green_zone_coordinates: List<String>,
    @SerialName("red_zone_coordinates") val red_zone_coordinates: List<String>,
    @SerialName("face_detection") val face_detection: Boolean
)

@Serializable
data class WorkplaceResponse(
    @SerialName("status") val status: String,
    @SerialName("workplace") val workplace: List<Workplace>,
    @SerialName("details") val details: String? = null
)