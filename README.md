# AndroidCore

[![](https://jitpack.io/v/appwise-labs/AndroidCore.svg)](https://jitpack.io/#appwise-labs/AndroidCore)

## Download

```groovy
implementation 'com.github.appwise-labs:AndroidCore:<Latest-Version>'
```

## Initialize

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

## Contribution

All contributors are welcome. Take a look at [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## Changelog

See the [CHANGELOG.md](CHANGELOG.md) for more information regarding new updates and more.

## License

See the [LICENSE](LICENSE) file for more info
