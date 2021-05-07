package com.slakra.domain.arch

interface BaseUseCase<T> {

    suspend fun execute(): ResultState<T>
}