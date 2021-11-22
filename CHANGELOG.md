# Change Log

All notable changes to this project will be documented in this file.

## [Master](https://github.com/appwise-labs/AndroidCore)

### Custom Views

### Breaking
- The signature of the `configureToolbar()` methods has been changed.
- Updated the visibility of some functions of the `BaseRestClient`.
- Added `GetGson()` back to the `BaseRestClient` so it can easily be extended with a `newBuilder`.

### Bug Fixes
- Cleaned up resources, removed unneeded imports, ...
- Changed immutable interceptor list mutable so the list can be easily added to.
- Fixed issue with the `logout()` function as it was breaking on Android API 31

### Improvements
- Fixed some styling issues with the ProfileActionButton
- Fixed issue with the textColor overriding the textAppearance color of a ProfileActionButton

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
