package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher

class GetCharacterUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Character>(dispatcher){
    override suspend fun block(param: Int): Character = repository.getCharacter(param)
}