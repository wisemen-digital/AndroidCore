# Realm

## Table of Contents

1. [Gradle Dependency](#gradle-dependency)
2. [Usage](#usage)

---

## Gradle Dependency

The `Realm` module contains some base classes that will enable you to use a standarized way of working with the Dao's, Repositories and more.

```groovy
dependencies {
  ...
  implementation 'com.github.appwise-labs.AndroidCore:realm:<Latest-Version>'
}
```

---

## Usage

In order to use all functionality of the `Realm` module it is necessary to instantiate some things in the `Application` class and in the `build.gradle` files.

First of all, in the `build.gradle` at app's level it is needed to add the plugin on top.

```groovy
plugins {
    id 'realm-android'
}
```

After that the `build.gradle` at project's level needs to be adjusted as well.

```groovy
dependencies {
    classpath "io.realm:realm-gradle-plugin:7.0.0"
}
```

NOTE!!!
Do mind that the 7.0.0 version only works if you have `jcenter()` added to the `repositories`. As `jcenter()` is being discontinued, it is required for us to use a newer version of Realm.

---

## Initialize

After the setup has been done, Realm can be initialized in the Application class like always.

The most used initialization is something like this

```kotlin
Realm.init(this)
val config = RealmConfiguration.Builder()
    .deleteRealmIfMigrationNeeded()
    .build()
Realm.setDefaultConfiguration(config)
```

In case it is needed to add a specific `Converter Factory` for Realm (in conjunction with a RestClient), just override the `createRetrofit()` method of the `Networking` module with this.

```kotlin
override fun createRetrofit(baseUrl: String): Retrofit {
    return super.createRetrofit(baseUrl).newBuilder()
        .addConverterFactory(RealmUtils.getRealmGsonFactory())
        .build()
}

```

---
