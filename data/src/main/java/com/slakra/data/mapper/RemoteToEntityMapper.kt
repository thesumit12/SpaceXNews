package com.slakra.data.mapper

interface RemoteToEntityMapper<R,E> {

    fun map(remote: R): E
}