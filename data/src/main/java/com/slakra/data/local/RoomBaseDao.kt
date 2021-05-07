package com.slakra.data.local

import androidx.room.*

interface RoomBaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertEntity(item: T): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWithReplaceStrategy(items: List<T>)

    @Update
    fun updateEntity(item: T): Int

    @Delete
    fun deleteEntity(item: T)
}