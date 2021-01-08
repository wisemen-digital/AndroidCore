package be.appwise.core.data.base


import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class BaseRoomDao<T : BaseEntity>(private val tableName: String) {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMany(entities: List<T>) : List<Long>

    @Update
    abstract suspend fun update(entity: T)

    @Update
    abstract suspend fun updateMany(entities: List<T>)

    @Delete
    abstract suspend fun delete(entity: T)

    @Delete
    abstract suspend fun deleteMany(entities: List<T>)

    @RawQuery
    abstract suspend fun deleteAllExceptIds(query: SupportSQLiteQuery) : Int

    suspend fun insertManyDeleteOthers(entities: List<T>) : List<Long> {
        val ids = entities.joinToString { it -> "\'${it.id}\'" }

        val query = SimpleSQLiteQuery("DELETE FROM $tableName WHERE id NOT IN($ids)")
        deleteAllExceptIds(query)

        return insertMany(entities)
    }
}