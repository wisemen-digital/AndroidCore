# Change Log

All notable changes to this project will be documented in this file.

## [Master](https://github.com/appwise-labs/AndroidCore)

### New Features

### Bug Fixes

### Documentation

### Breaking

### Custom Views

### Improvements
- The generic function to get a service is limited to extensions of `BaseService`.
- Deprecated everything regarding Bagel, instead Proxyman is the next successor.
- Updated some dependencies!

## [1.4.2](https://github.com/appwise-labs/AndroidCore/releases/tag/1.4.2)

### New Features

### Bug Fixes
- Fixed thread bug for the coroutine exception

### Documentation

### Breaking

### Custom Views

### Improvements
- Created a function to get a service in a more generic way for the networking client
- A status code can now be found when an Api Error occurs.

## [1.4.1](https://github.com/appwise-labs/AndroidCore/releases/tag/1.4.1)

### Improvements
- Removed the deprecation warnings of the `doCall` function as this must still be backwards compatible.


## [1.4.0](https://github.com/appwise-labs/AndroidCore/releases/tag/1.4.0)

### New Features
- Added DataRow (+ extras) to the project.
- Added a new way to do our Network calls. Errors can now be handled on a 'per call' basis instead of just a flat-out exception. The old way is still available for older projects.

### Bug Fixes
- Fixed issue where the `allPermissionsGranted` was a false positive
- Fixed some crashes for ProxyMan so the app won't crash when it's run on an emulator or a ProxyMan client cannot be found anymore.

### Documentation
- Added required dependencies when importing Room or Core

### Breaking

### Custom Views
- Added DataRow (+ extras) to the project.

### Improvements
- Reworked the ViewModelExtensions a bit to add a general `viewModelFactory` function to the list. It accepts a ViewModel without any restrictions to the parameters. Also deprecated some functions.


## [1.3.0](https://github.com/appwise-labs/AndroidCore/releases/tag/1.3.0)

### New Features
- Created Measurement API to easily convert between units and also format the description with the correct Locale.

### Bug Fixes
- Added `getConverterFactories()` to the `BaseRestClient` so we can reorder ConverterFactories if needed.
- Changed the default exception handler in the `BaseViewModel` so it will use a LiveData callback instead of a high order function.
This makes sure that errors in a coroutine in the `init{}` block can be handled as well

### Improvements
- Changed the way we provide a function to create/show a default snackbar.

## [1.2.0](https://github.com/appwise-labs/AndroidCore/releases/tag/1.2.0)

### Breaking
- The signature of the `configureToolbar()` methods has been changed.
- Updated the visibility of some functions of the `BaseRestClient`.
- Added `GetGson()` back to the `BaseRestClient` so it can easily be extended with a `newBuilder`.

### Bug Fixes
- Cleaned up resources, removed unneeded imports, ...
- Changed immutable interceptor list mutable so the list can be easily added to.
- Fixed issue with the `logout()` function as it was breaking on Android API 31
- Fixed the issue where the sourceCode could not be inspected whilst using the modules as a dependency

### Improvements
- Fixed some styling issues with the ProfileActionButton
- Fixed issue with the textColor overriding the textAppearance color of a ProfileActionButton
- Added paging3 module to the build, it will provide some standard ways to add a paging source and will easily hook in to your current implementation with a repository interface and much more.

## [1.1.0](https://github.com/appwise-labs/AndroidCore/releases/tag/1.1.0)

### Custom Views
- Added classes that are used to create fast, easy and commonly used views/components.
- This update contains InitialsImageView, ProfileActionButton , ProfileDataRow , Numberstepper


### Breaking

- Moved Core to a proper module, now a better abstraction can be achieved by adding more submodules to Core. Even a sample app can be created within this project as well. Details of the procedure can be found [here](https://appwise.atlassian.net/wiki/spaces/~635004520/pages/415989761/Android+Core+Multiple+Submodules).
- Networking, Realm and Room have been moved to separate modules as well.

### Bug Fixes

- Removed 'JCenter' from the repositories list as that [service has been discontinued](https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/).
- Removed 'easyValidation' dependency as it was a deprecated library that was only available on JCenter.

### Improvements
- Added the possibility to override the choose if you want the Proxyman classes to show it's logs.
- Limited the number of calls/packages Proxyman holds in memory when there is no client available.
- Removed unneeded line that deleted Proxyman clients that weren't connected to your app anymore.
- Added the possibility to override the BaseRestClient's `getHttpLoggingInterceptor()` to provide a project specific logging level.

## [1.0.0](https://github.com/appwise-labs/AndroidCore/releases/tag/1.0.0)

### New Features

- Added Double and Tripple triggers for LiveData.
- `tryNavigate` functions have been added.
- Added `ProxymanInterceptor` and more, now we can debug our request/response data for calls in the Proxyman mac app.

### Breaking

- Removed Realm from AndroidCore and replaced it with Room.
- Updated `EasyImage` dependency which had breaking changes in some of our Extension functions.
- Updated a lot of dependencies where a couple of them had some breaking changes, not major but still (functions turned to parameters).
- Using `BaseBindingVMFragment`, `BaseVMFragment`, `BaseBindingVMActivity` or `BaseVMActivity` won't require you to add the ViewModel as a generic object

### Bug Fixes

- Fixed issue with our `parseError` where we didn't want to use a new Retorfit instance.
- FadingEdge will now work when adding padding to the `RecyclerViewEmptyLoadingSupport`.
- `kotlin_version` added to `build.gradle` so our dependabot won't fail it's builds.
- BaseEntity now expects an `id` param as an `Any` type.
- Extension function `setupRecyclerView` is now more flexible.
- Changed expected type of `id` from `Int` to `Any`

### Improvements

- Added an `extraLogoutStep()` in `BaseNetworkingListeners` so it's easier to potentially keep some values in Hawk when logging out.
- Added possibility to provide a custom `idColumnInfo` when needed in the `BaseDao`, default value will still be `id`
- Added possibility to add multiple ApiServices to a RestClient (possibility to use different baseUrl as well)

### Internal

- Added dependabot to be up-to-date with any dependency changes.

## [0.1.0-rc1](https://github.com/appwise-labs/AndroidCore/releases/tag/0.1.0-rc1)

First version of AndroidCore.

### New Features

- Added a couple of Extension Functions that are used a lot.
- Added a basic implementation for a RestClient, a lot is customizable (baseUrl, protected, headers, interceptor, ...)
- Added other Base class for eas of use (BaseFragment, BaseActivity, BaseViewModel, ...)
  - BaseFragment/BaseActivity for use with DataBinding and more are also already available
- Added a lot of dependencies that are use in almost all of our projects
- Added HawkValueDelegate
