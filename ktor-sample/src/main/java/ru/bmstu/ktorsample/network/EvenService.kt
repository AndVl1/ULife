package ru.bmstu.ktorsample.network

interface EvenService {
    suspend fun getEven(num: Int): EvenResponse
}