package goryachkovskiy.danil.mtuci.kmm.data_remote.model.mapper

import goryachkovskiy.danil.mtuci.kmm.data_remote.model.ApiCharacter
import goryachkovskiy.danil.mtuci.kmm.data_remote.model.ApiWorkplace
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import goryachkovskiy.danil.mtuci.kmm.domain.model.Gender
import goryachkovskiy.danil.mtuci.kmm.domain.model.Status
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import goryachkovskiy.danil.mtuci.kmm.domain.model.map.Mapper

class ApiCharacterMapper : Mapper<ApiCharacter, Character>() {
    override fun map(model: ApiCharacter): Character = model.run {
        Character(
            id, name, when (status) {
                "Alive" -> Status.ALIVE
                "Dead" -> Status.DEAD
                else -> Status.UNKNOWN
            }, species, when (gender) {
                "Male" -> Gender.MALE
                "Female" -> Gender.FEMALE
                "Genderless" -> Gender.GENDERLESS
                else -> Gender.UNKNOWN
            }, origin.name, location.name, image
        )
    }

    override fun inverseMap(model: Character): ApiCharacter {
        TODO("Not yet implemented")
    }
}

class ApiWorkplaceMapper : Mapper<ApiWorkplace, Workplace>() {
    override fun map(model: ApiWorkplace): Workplace = model.run {
        Workplace(
            id= 0,
            max_people = max_people,
            current_people = current_people,
            title = title,
            camera_url = camera_url,
            green_zone_coordinates = green_zone_coordinates,
            red_zone_coordinates = red_zone_coordinates,
            face_detection = face_detection,
        )
    }

    override fun inverseMap(model: Workplace): ApiWorkplace {
        TODO("Not yet implemented")
    }
}