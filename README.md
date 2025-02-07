# AndroidCore

[![](https://jitpack.io/v/appwise-labs/AndroidCore.svg)](https://jitpack.io/#appwise-labs/AndroidCore)

1. [Legacy](#legacy)
2. [Modules](#modules)
   1. [Core](#core)
   2. [Networking](#networking)
   3. [Room](#room)
   5. [Paging](#paging)
   6. [EmptyStateRecyclerView](#emptystaterecyclerview)
3. [Contribution](#contribution)
4. [Changelog](#changelog)
5. [License](#license)

---

# Legacy

Using a version before v1.1.0 of this dependency can be used and will suffice, as it will already contain Networking, Room and the EmptyStateRecyclerView.

```groovy
implementation 'com.github.appwise-labs:AndroidCore:<Latest-Version>'
```

---

# Modules

Since v1.1.0 the library has grown to use multiple modules for specific use-cases. The core module contains a lot of functionality that some of the other modules may use.

In the core module a lot of general extension functions can be found, as well as some BaseClasses regarding the ViewModels, Fragments, and Activities. Also a lot of dependencies are already added to core which are 'made public' so they can be used through Core.

---

## Core

[Core Samples and Information](documentation/CORE.md)

The core can be added by using this dependency. It contains BaseClasses for ViewModel, Fragments, and Activities. Also a lot of extension functions can be found.

```groovy
implementation 'com.github.appwise-labs.AndroidCore:core:<Latest-Version>'
```

Don't forget to also add the "navigation-safe-args" plugin to the project level build.gradle

```groovy
dependencies {
  classpath "androidx.navigation:navigation-safe-args-gradle-plugin:<Latest-Navigation-Version>"
}
```

---

## Networking

[Networking Samples and Information](documentation/NETWORKING.md)

Using this module will enable you to use the BaseRestClient object with some default values regarding the RestClient and Interceptors, a lot of which is costumizable.

```groovy
implementation 'com.github.appwise-labs.AndroidCore:networking:<Latest-Version>'
```

---

## Room

[Room Samples and Information](documentation/ROOM.md)

With this Room module you will have access to a BaseRoomDao in which a lot of queries are premade. The BaseRoomDao will expect a BaseEntity, so make sure that your Room Entity extends that.

```groovy
implementation 'com.github.appwise-labs.AndroidCore:room:<Latest-Version>'

kapt 'androidx.room:room-compiler:<Latest-Room-Version>'
```

---

## Paging

[Paging Samples and Information](documentation/PAGING.md)

```groovy
implementation 'com.github.appwise-labs.AndroidCore:paging:<Latest-Version>'
```

---

## EmptyStateRecyclerView

[EmptyStateRecyclerView Samples and Information](documentation/EMPTY_STATE_RECYCLERVIEW.md)

This module contains a custom view extending from the RecyclerView which can handle different states like Loading, Normal, and Empty.

```groovy
implementation 'com.github.appwise-labs.AndroidCore:emptyRecyclerView:<Latest-Version>'
```

---

# Contribution

All contributors are welcome. Take a look at [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

---

# Changelog

See the [CHANGELOG.md](CHANGELOG.md) for more information regarding new updates and more.

---

# License

See the [LICENSE](LICENSE) file for more info
