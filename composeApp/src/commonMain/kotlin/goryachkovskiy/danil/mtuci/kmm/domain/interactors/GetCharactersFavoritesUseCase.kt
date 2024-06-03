package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCaseFlow
import goryachkovskiy.danil.mtuci.kmm.domain.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, List<Character>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<Character>> = repository.getCharactersFavorites()
}