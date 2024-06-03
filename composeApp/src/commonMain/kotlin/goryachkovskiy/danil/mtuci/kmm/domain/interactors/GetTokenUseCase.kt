package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetTokenUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, String>(dispatcher) {
    override suspend fun block(param: Unit): String = repository.getToken()
}