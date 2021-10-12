package be.appwise.paging.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class BaseRemoteKeysDao<T : BaseRemoteKeys>(private val tableName: String) {
    open val idColumnInfo = "itemId"

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(remoteKey: List<T>)

    suspend fun remoteKeyId(id: Any): T? {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $idColumnInfo = $id")
        return selectQuery(query)
    }

    suspend fun clearRemoteKeys() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName")
        deleteKeys(query)
    }

    @RawQuery
    protected abstract suspend fun deleteKeys(query: SupportSQLiteQuery): Int

    @RawQuery
    protected abstract suspend fun selectQuery(query: SupportSQLiteQuery): T?
}