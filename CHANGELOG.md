# Change Log

All notable changes to this project will be documented in this file.

## [Master](https://github.com/appwise-labs/AndroidCore)

### New Features

- Added Double and Tripple triggers for LiveData.
- `tryNavigate` functions have been added.

### Breaking

- Removed Realm from AndroidCore and replaced it with Room.
- Updated `EasyImage` dependency which had breaking changes in some of our Extension functions.
- Updated a lot of dependencies where a couple of them had some breaking changes, not major but still (functions turned to parameters).

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
