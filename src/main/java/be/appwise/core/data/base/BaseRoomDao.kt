package be.appwise.core.data.base


import androidx.room.*

@Dao
interface BaseRoomDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(entities: List<T>) : List<Long>

    @Update
    suspend fun update(entity: T)

    @Update
    suspend fun updateMany(entities: List<T>)

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun deleteMany(entities: List<T>)
}