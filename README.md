# AndroidCore

[![](https://jitpack.io/v/appwise-labs/AndroidCore.svg)](https://jitpack.io/#appwise-labs/AndroidCore)

## <u>Download</u>

```groovy
implementation 'com.github.appwise-labs:AndroidCore:<Latest-Version>'
```

From v1.1.0 the library has grown to use multiple modules for specific use-cases. 
The core can be added by using this dependency.
```groovy
implementation 'com.github.appwise-labs:AndroidCore:core:<Latest-Version>'
```

To use another module of this library just follow the folder structure
```groovy
implementation 'com.github.appwise-labs:AndroidCore:list:emptyRecyclerView:<Latest-Version>'
```


## <u>Initialize</u>

In order to initialize AndroidCore correctly you have 2 options.

- CoreApp: to initialze the Logger, ErrorActivity and Hawk (Hawk is initialized by default by the `init()` on `CoreApp`
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

When implementing a Restclient in your project you can extend from `BaseRestClient`. Do mind that you still have a choice to implement the RestClient as an `object` or as a regular `class`. (Each time you call the class `AppRestClient()` it'll create a new instance, while calling the object `AppRestClient` retains the same instance)

In your RestClient you can have multiple apiServices to accomodate for the multitude of Repositories in the project, or you can have 1 single apiService.

```kotlin
object UnProtectedRestClient : BaseRestClient(){

    override val protectedClient = false
    override fun getBaseUrl() = "https://www.apiEndpoint.com/api/v1/"

    val userService: UserNetworkService by lazy {
        // Use this function ('createRetrofit()') when you wish to use a different 'baseUrl' with your service
        createRetrofit("https://www.apiEndpoint.com/api/v1/user")
            .create(PokemonNetworkService::class.java)
    }

    val authService: AuthNetworkService by lazy {
        // Use this when you just want to use a different service with the default 'getBaseUrl()'
        getRetrofit.create(MatchUpNetworkService::class.java)
    }

    val settingsService: SettingsNetworkService by lazy {
        // This behaves the same as using 'createRetrofit(baseUrl)',
        // but offers more options by using the builder itself.
        getRetrofit.newBuilder()
            .baseUrl("https://www.apiEndpoint.com/api/v1/settings")
            .build()
            .create(SettingsNetworkService::class.java)
    }
}
```

Within the RestClient class, you have a lot of flexibility to adjust it to your requirements. Just take look at the possible functions and parameters to override.

<p align="center">
  <img width="250" src="static/RestClient-flexibility.png">
</p>

## <u>ProxymanInterceptor</u>

The ProxymanInterceptor is a interceptor that sends request/response data over local network to services that are running Proxyman. If you want to use the ProxymanInterceptor you have to do follow these steps

1. Register the ProxymanService (discovery of Proxyman services/clients on local network), the second parameter is optional and will be used as the device identifier in the Proxyman client, when none is given a your device's name will be used. As a third parameter, you have the option to limit the clients which will receive your network packages when they are running Proxyman. as a fourth parameter , you have the option to enable and disable logging for the Proxyman classes.

```
Networking.Builder()
    .registerProxymanService(this, "OnePlus-Appwise", arrayListOf("MacBook-1", "MacBook-Personal"),false)
    .build()
```

2. Add ProxymanInterceptor to instance of BaseRestclient by overriding enableProxyManInterceptor()

```kotlin
object ProtectedRestClient : BaseRestClient<NetworkService>(){
        override fun enableProxyManInterceptor() = Buildflavor == "dev"
}
```

---

**NOTE:** Make sure you disable the ProxymanInterceptor on Release builds by checking BuildConfig.Debug. If you use the new CI file it will be ok. If are not using the new file check if your **Buildflavor == "dev"**.

---

3. Make sure your Android device and the service running Proxyman are on the same network.

4. Enjoy!

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

Another possibility is to add the constants in a `companion object` in the classes where the constants are needed in.

Doing either of this will enable you to provide your table and column names in queries like so:

```kotlin
@Query("SELECT * FROM ${DBConstants.POEM_TABLE_NAME} WHERE ${DBConstants.COLUMN_ID_POEM} = :poemId")
```

Should you ever need to change those values, then you'd only have to do so in one location.

## <u>Base Activity Fragment</u>

To reduce some boilerplate code whilst creating new Fragments or Activities you can use these BaseClasses

- BaseFragment | BaseActivity
- BaseVMFragment | BaseVMActivity
- BaseBindingVMFragment |BaseBindingVMActivity

Do mind, that by using the `BaseBindingVM` equivalent you will automatically be using the other 2 as well. The hierarchy is as follows:

`BaseBindingVMFragment` -------extends-------> `BaseVMFragment` -------extends-------> `BaseFragment`

### <u>BaseVM...</u>

By using `BaseVM...` you will need to override the value `mViewModel`. After that is set, the `DefaultExceptionHandler` will also be set automatically

```kotlin
override val mViewModel: MainViewModel by viewModels()
```

With `BaseVM...` you also have the option to add a custom `viewModelFactory` to it. For this to work you just need to override the function `getViewModelFactory()`

```kotlin
override fun getViewModelFactory() = SplashViewModel.factory("someValue")
```

You can then add the factory to the declaration of the `mViewModel`

```kotlin
override val mViewModel: MainViewModel by viewModels() {
    MainViewModel.factory("someValue")
}
```

### <u>BaseBindingVM...</u>

Everything that applies to `BaseVM...` applies to this class as well. A couple of things to add to this is that the Binding class is an expected Generic. You'll also have to override a function `getLayout()` to finish the basic setup for DataBinding

```kotlin
class MainFragment : BaseBindingVMFragment<FragmentMainBinding>() {
    override fun getLayout() = R.layout.fragment_main

}
```

If you have any variables in your layout that you wish to dataBind to it, you'll have to do that yourself in the `onCreate()` (for Activities) or `onViewCreated()` (for Fragments)

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mBinding.run {
        viewModel = mViewModel
    }
}

// OR

override fun onViewCreated(view: View,s savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    mBinding.run {
        viewModel = mViewModel.apply {
            // After the viewModel has been initialized you can use the functions and variables like normal
            fetchData()
        }
    }
}

```

## <u>Contribution</u>

All contributors are welcome. Take a look at [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## <u>Changelog</u>

See the [CHANGELOG.md](CHANGELOG.md) for more information regarding new updates and more.

## <u>License</u>

See the [LICENSE](LICENSE) file for more info
