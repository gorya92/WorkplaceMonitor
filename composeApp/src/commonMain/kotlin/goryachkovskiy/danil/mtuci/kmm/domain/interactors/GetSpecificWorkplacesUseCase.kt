package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import kotlinx.coroutines.CoroutineDispatcher

class GetSpecificWorkplacesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Workplace>(dispatcher){
    override suspend fun block(param: Int): Workplace = repository.getSpecificWorkplacesFromApi(param)
}