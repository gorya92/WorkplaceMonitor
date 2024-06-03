package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCase
import goryachkovskiy.danil.mtuci.kmm.domain.model.Workplace
import kotlinx.coroutines.CoroutineDispatcher

class GetWorkplacesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<String, List<Workplace>>(dispatcher){
    override suspend fun block(param: String): List<Workplace> {
        repository.setTokenFromApi(param)
       return repository.getWorkplacesFromApi(param)
    }
}