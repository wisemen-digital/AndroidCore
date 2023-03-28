# Networking

## Table of Contents

1. [Gradle Dependency](#gradle-dependency)
2. [Usage](#usage)
3. [RestClient](#restclient)
4. [Proxyman Interceptor](#proxymaninterceptor)

---

## Gradle Dependency

The `Networking` module contains base classes and extensions to add Networking (through Retrofit) to the application.

```groovy
dependencies {
  ...
  implementation 'com.github.appwise-labs.AndroidCore:networking:<Latest-Version>'
}
```

---

## Usage

In order to use all functionality of the `Networking` module it is necessary to instantiate some things in the `Application` class.

```kotlin

Networking.Builder()
    .registerProxymanService(this)
    .setPackageName(packageName)
    .setAppName(getString(R.string.app_name))
    .setVersionCode(BuildConfig.VERSION_CODE.toString())
    .setVersionName(BuildConfig.VERSION_NAME)
    .setClientSecretValue(BuildConfig.CLIENT_SECRET)
    .setClientIdValue(BuildConfig.CLIENT_ID)
    .setApiVersion(BuildConfig.API_VERSION)
    .build()
```

After this is done you can start off by extending the `RestClient`.

---

## RestClient

When implementing a Restclient in your project you can extend from `BaseRestClient`. Don't forget to add the `API Service Interface` inside the `<>`. Do mind that you still have a choice to implement the RestClient as an `object` or as a regular `class`.

When implementing a Restclient in your project you can extend from `BaseRestClient`. Do mind that you still have a choice to implement the RestClient as an `object` or as a regular `class`. (Each time you call the class `AppRestClient()` it'll create a new instance, while calling the object `AppRestClient` retains the same instance)

In your RestClient you can have multiple apiServices to accommodate for the multitude of Repositories in the project, or you can have 1 single apiService.

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
  <img width="250" src="../static/RestClient-flexibility.png">
</p>

---

## ProxymanInterceptor

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
