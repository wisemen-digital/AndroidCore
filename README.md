# AndroidCore

[![](https://jitpack.io/v/appwise-labs/AndroidCore.svg)](https://jitpack.io/#appwise-labs/AndroidCore)

1. [Legacy](#legacy)
2. [Modules](#modules)
   1. [Core](#core)
   2. [Networking](#networking)
   3. [Room](#room)
   4. [Realm](#realm)
   5. [EmptyStateRecyclerView](#emptystaterecyclerview)
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

---

## Networking

[Networking Samples and Information](documentation/NETWORKING.md)

```groovy
implementation 'com.github.appwise-labs.AndroidCore:networking:<Latest-Version>'
```

---

## Room

[Room Samples and Information](documentation/ROOM.md)

```groovy
implementation 'com.github.appwise-labs.AndroidCore:room:<Latest-Version>'
```

---

## Realm

[Realm Samples and Information](documentation/REALM.md)

```groovy
implementation 'com.github.appwise-labs.AndroidCore:realm:<Latest-Version>'
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
