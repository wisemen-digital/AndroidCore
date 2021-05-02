# AndroidCore

[![](https://jitpack.io/v/appwise-labs/AndroidCore.svg)](https://jitpack.io/#appwise-labs/AndroidCore)

## <u>Download</u>

```groovy
implementation 'com.github.appwise-labs:AndroidCore:<Latest-Version>'
```

## <u>Initialize</u>

In order to initialize AndroidCore correctly you have 2 options.

- CoreApp: to initialze the Logger, ErrorActivity and Hawk (Hawk is initialized by the `init()` on `CoreApp`
- Networking: to initialize everything regarding the networking part of Core

```kotlin
CoreApp.init(this)
    .initializeErrorActivity(BuildConfig.DEBUG)
    .initializeLogger()
    .build()

Networking.Builder()
    .registerBagelService(this)
    .setPackageName(packageName)
    .setAppName(getString(R.string.app_name))
    .setVersionCode(BuildConfig.VERSION_CODE.toString())
    .setVersionName(BuildConfig.VERSION_NAME)
    .setClientSecretValue(BuildConfig.CLIENT_SECRET)
    .setClientIdValue(BuildConfig.CLIENT_ID)
    .setApiVersion(BuildConfig.API_VERSION)
    .build()
```

Do note, that when you use AndroidCore as a Dependency that the `isLoggable` parameter in `initializeLogger()` should be added. It is best to use `BuildConfig.DEBUG` as the parameter so the logs won't show up in a production build.

## <u>RestClient</u>

When implementing a Restclient in your project you can extend from `BaseRestClient`. Don't forget to add the `API Service Interface` inside the `<>`. Do mind that you still have a choice to implement the RestClient as an `object` or as a regular `class`.

```kotlin
object UnProtectedRestClient : BaseRestClient<NetworkService>(){
    override val apiService = NetworkService::class.java
    override val protectedClient = false

    override fun getBaseUrl() = "https://www.baseUrl.com/"
}
```

Within the RestClient class, you have a lot of flexibility to adjust it to your requirements. Just take look at the possible functions and parameters to override.

<p align="center">
  <img width="250" src="static/RestClient-flexibility.png">
</p>

## <u>Room</u>

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

### Custom 'id' column names

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

Doing this will enable you to provide your table and column names in queries like so:

```kotlin
@Query("SELECT * FROM ${DBConstants.POEM_TABLE_NAME} WHERE ${DBConstants.COLUMN_ID_POEM} = :poemId")
```

Should you ever need to change those values, then you'd only have to do so in one location.

## <u>Contribution</u>

All contributors are welcome. Take a look at [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## <u>Changelog</u>

See the [CHANGELOG.md](CHANGELOG.md) for more information regarding new updates and more.

## <u>License</u>

See the [LICENSE](LICENSE) file for more info
