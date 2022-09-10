package ru.bmstu.ktorsample.main.input

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.bmstu.ktorsample.base.Repository
import ru.bmstu.ktorsample.network.EvenResponse
import ru.bmstu.ktorsample.network.EvenService

class InputRepository(private val service: EvenService) : Repository {
    suspend fun checkEven(num: Int): EvenResponse {
        return withContext(Dispatchers.IO) {
            service.getEven(num)
        }
    }
}
