package be.appwise.core.data.base

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * A default implementation of a Room Dao that holds the basic function to perform CRUD operations
 * on a specified *Entity*
 *
 * @param T the type of the Entity. This type must implement [BaseEntity]
 * @property tableName the name of the table on which the DAO needs to perform queries
 * @author Sibert Schurmans
 */
@Dao
abstract class BaseRoomDao<T : BaseEntity>(private val tableName: String) {
    open val idColumnInfo = "id"

    /**
     *  Inserts an entity object in the database.
     *  If the provided ID from the entity already exists, the object will be overwritten.
     *
     *  @param entity the object that needs to be inserted
     *  @return the ID of the inserted entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: T) : Long

    /**
     *  Inserts multiple entity objects in the database.
     *  If the provided ID from an entity in the list already exists, the object will be overwritten.
     *
     *  @param entities a list of objects to be inserted
     *  @return a list of ID's from the inserted entities
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMany(entities: List<T>) : List<Long>

    /**
     *  Updates an entity object in the database.
     *  It is also possible to use [insert] instead of this function
     *
     *  @param entity the object that needs to be updated
     *  @return the ID of the updated entity
     */
    @Update
    abstract suspend fun update(entity: T)

    /**
     *  Updates multiple entity objects in the database.
     *  It is also possible to use [insertMany] instead of this function
     *
     *  @param entities a list of objects that needs to be updated
     *  @return a list of ID's from the updated entities
     */
    @Update
    abstract suspend fun updateMany(entities: List<T>)

    /**
     *  Deletes an entity object from the database.
     *
     *  @param entity the object that needs to be deleted
     */
    @Delete
    abstract suspend fun delete(entity: T)

    /**
     *  Deletes multiple entity objects from the database.
     *  If you want to delete all entities from the table and insert it with new ones,
     *  you can use [insertManyDeleteOthers]
     *
     *  @param entities a list of objects that needs to be deleted
     */
    @Delete
    abstract suspend fun deleteMany(entities: List<T>)

    @RawQuery
    protected abstract suspend fun deleteAllExceptIds(query: SupportSQLiteQuery) : Int

    @RawQuery
    protected abstract suspend fun deleteAllFromTable(query: SupportSQLiteQuery) : Int

    @RawQuery
    protected abstract suspend fun deleteById(query: SupportSQLiteQuery) : Int

    @RawQuery
    protected abstract suspend fun findMultipleEntities(query: SupportSQLiteQuery): List<T>?

    @RawQuery
    protected abstract suspend fun findSingleEntity(query: SupportSQLiteQuery): T?

    /**
     *  Deletes all entities that are not inside the provided list and insert / replace the entities
     *  inside the list
     *
     *  @param entities the list of objects that needs to be inserted
     *  @return a list of the inserted ID's
     */
    suspend fun insertManyDeleteOthers(entities: List<T>) : List<Long> {
        val ids = entities.joinToString { "\'${it.id}\'" }

        val query = SimpleSQLiteQuery("DELETE FROM $tableName WHERE $idColumnInfo NOT IN($ids);")
        deleteAllExceptIds(query)

        return insertMany(entities)
    }

    /**
     *  Deletes all entities from the table
     */
    suspend fun deleteAllFromTable() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName;")
        deleteAllFromTable(query)
    }

    /**
     *  Deletes the entity from the table that matches the given ID
     *
     *  @param id the id of the entity that needs to be deleted
     */
    suspend fun deleteById(id: Any) {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName WHERE $idColumnInfo = $id;")
        deleteById(query)
    }

    /**
     * Get all entities from the table
     *
     * @return a list of all entities
     */
    suspend fun findAllEntities(): List<T>? {
        return findMultipleEntities(SimpleSQLiteQuery("SELECT * FROM $tableName;"))
    }

    /**
     * Search specific entities inside the table
     *
     * @param ids a list of ID's that needs to be searched for
     * @return a list of all entities that match the ID's from the given list
     */
    suspend fun findEntitiesById(ids: List<Any>): List<T>? {
        val formattedIds = ids.joinToString { "\'${it}\'" }

        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $idColumnInfo IN ($formattedIds);")
        return findMultipleEntities(query)
    }

    /**
     * Search a specific entity inside the table
     *
     * @param id the ID of the object that needs to be searched for
     * @return the object if the ID matched
     */
    suspend fun findEntityById(id: Any): T? {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $idColumnInfo = $id")
        return findSingleEntity(query)
    }

    suspend fun findEntitiesWithKeyword(column: String, needle: List<String>): List<T>? {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $column LIKE '%' || $needle || '%'")
        return findMultipleEntities(query)
    }

    /**
     * LIVEDATA - ASYNC FUNCTIONS
     *
     * The following functions are currently not working due to the fact that Room requires the
     * annotations to be a constant value and available on compile time.
     * There is also a possibility to work with ComputableLiveData but this API is not public.
     *
     * More research / time required
     */

    /*
    @RawQuery(observedEntities = /* ARRAY OF ALL ENTITIES */)
    abstract fun findMultipleEntitiesLive(query: SupportSQLiteQuery): LiveData<List<T>>

    @RawQuery(observedEntities = /* ARRAY OF ALL ENTITIES */)
    abstract fun findSingleEntityLive(query: SupportSQLiteQuery): LiveData<T>
    */

    /*
    fun findEntityLive(): LiveData<List<T>> {
        return object : ComputableLiveData<List<T>>() {
            private var observer: InvalidationTracker.Observer? = null
            override fun compute(): List<T>? {
                if (observer == null) {
                    observer = object : InvalidationTracker.Observer(tableName) {
                        override fun onInvalidated(tables: Set<String>) = invalidate()
                    }
                roomDatabase.invalidationTracker
                            .addWeakObserver(observer)
                }
                return findEntityById(id)
            }
        }.liveData
     }

    fun findAllEntitiesLive(): LiveData<List<T>> {
        return findMultipleEntitiesLive(SimpleSQLiteQuery("SELECT * FROM $tableName;"))
    }

    fun findEntitiesByIdLive(ids: List<Any>): LiveData<List<T>> {
        val formattedIds = ids.joinToString { it -> "\'${it}\'" }

        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $idColumnInfo IN ($formattedIds);")
        return findMultipleEntitiesLive(query)
    }

    fun findEntityByIdLive(id: Any): LiveData<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $idColumnInfo = $id")
        return findSingleEntityLive(query)
    }*/
}