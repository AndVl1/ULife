package ru.bmstu.ktorsample.main.input

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.bmstu.ktorsample.base.Repository
import ru.bmstu.ktorsample.network.KrResponse
import ru.bmstu.ktorsample.network.EvenService

class InputRepository(private val service: EvenService) : Repository {
    suspend fun checkEven(num: Int): KrResponse {
        return withContext(Dispatchers.IO) {
            service.getEven(num)
        }
    }
}
