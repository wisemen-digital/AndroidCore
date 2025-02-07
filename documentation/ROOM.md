# Room

## Table of Contents

1. [Gradle Dependency](#gradle-dependency)
2. [Using @Transaction](#using-transaction)
3. [Custom 'id' column name](#custom-id-column-name)

---

## Gradle Dependency

The `Room` module contains some base classes that will enable you to use a standarized way of working with `Entities` and `Dao`s

```groovy
dependencies {
  implementation 'com.github.wisemen-digital.AndroidCore:room:<Latest-Version>'

  kapt "androidx.room:room-compiler:<Latest-Room-Version>"
}
```

---

## Using @Transaction

In case you wish to use `@Transaction` in combination with AndroidCore's `BaseRoomDao`, you should use an `open` keyword with your function.

```kotlin
@Dao
abstract class Dao: BaseRoomDao<Item>("item"){

    @Insert
    abstract fun insert(item: Item)

    @Delete
    abstract fun delete(item: Item)

    @Transaction
    open fun replace(oldItem: Item, newItem: Item){
        delete(oldItem)
        insert(newItem)
    }
}
```

In case you omit the `open` keyword you'll get an error `error: Method annotated with @Transaction must not be private, final, or abstract.`

---

## Custom 'id' column name

When using `BaseEntity` and you wish to provide a different name for the `id` column, you can do so by using the `@ColumnInfo` on your Entity. Doing this, however, breaks the standard use of our `BaseDao` class. For it to work, just overridde the `idColumnInfo` in your Dao to tie it together.

```kotlin
override val idColumnInfo = DBConstants.COLUMN_ID_USER
```

I suggest to use a class to put all your Database constants in, i.e.:

```kotlin
object DBConstants {
    const val DATABASE_NAME = "poemCollection.db"
    const val USER_TABLE_NAME = "user"

    const val COLUMN_ID_USER = "userId"
}
```

Another possibility is to add the constants in a `companion object` in the classes where the constants are needed in.

Doing either of this will enable you to provide your table and column names in queries like so:

```kotlin
@Query("SELECT * FROM ${DBConstants.POEM_TABLE_NAME} WHERE ${DBConstants.COLUMN_ID_POEM} = :poemId")
```

Should you ever need to change those values, then you'd only have to do so in one location.
