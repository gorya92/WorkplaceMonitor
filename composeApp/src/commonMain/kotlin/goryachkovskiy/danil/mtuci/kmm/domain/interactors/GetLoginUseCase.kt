package goryachkovskiy.danil.mtuci.kmm.domain.interactors

import goryachkovskiy.danil.mtuci.kmm.domain.IRepository
import goryachkovskiy.danil.mtuci.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
data class AuthParams(val login: String, val password: String)


class GetLoginUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,

    ) : BaseUseCase<AuthParams, String>(dispatcher){
    override suspend fun block(param: AuthParams): String {
        println("VIEW" + param.login + param.password)
       val res =  repository.auth(param.login, param.password)
        println("VIEW" + res.toString())
        return res
    }
}