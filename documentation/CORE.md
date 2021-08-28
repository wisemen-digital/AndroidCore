# Core

## Table of Contents

1. [Gradle Dependency](#gradle-dependency)
2. [Initialize](#initialize)
3. [Base Classes](#base-classes)
   1. [BaseVM...](#basevm)
   2. [BaseBindingVM...](#basebindingvm)

---

## Gradle Dependency

This Core module can be added by using this dependency. It contains BaseClasses for ViewModel, Fragments, and Activities. Also a lot of extension functions can be found.

```groovy
dependencies {
  ...
  implementation 'com.github.appwise-labs.AndroidCore:core:<Latest-Version>'
}
```

Android Studio could prompt you with an error if you're building an app with this library as a dependeny. To fix that issue just add the next code snippet.

```
packagingOptions {
    exclude 'META-INF/core_release.kotlin_module'
}
```

The reason behind this is that another library that's called `core` is added to the build as well which is causing an issue [more can be read here](https://discuss.kotlinlang.org/t/disable-meta-inf-generation-in-gradle-android-project/3830).

A possible solution is to rename our Core library to AppwiseCore or something

---

## Initialize

The Core module, at the moment, can technically be used without initialization. Though, to use the full functionality it is recommended to do the initialization. This can be done in the `Application` class.

Doing so will give you a couple of options, you can initialize a Default Error Activity to show instead of the old (and ugly) pop-up of Android itself. Another option is to set if the Logger should be active for this build and which tag it should use by default.

```kotlin
CoreApp.init(this)
    .initializeErrorActivity(BuildConfig.DEBUG)
    .initializeLogger("LoggingTag", BuildConfig.DEBUG)
    .build()
```

Do note, that when you use AndroidCore as a Dependency that the `isLoggable` parameter in `initializeLogger()` should be added. It is best to use `BuildConfig.DEBUG` as the parameter so the logs won't show up in a production build.

---

## Base classes

To reduce some boilerplate code whilst creating new Fragments or Activities you can use these BaseClasses

- BaseFragment | BaseActivity
- BaseVMFragment | BaseVMActivity
- BaseBindingVMFragment |BaseBindingVMActivity

Do mind, that by using the `BaseBindingVM` equivalent you will automatically be using the other 2 as well. The hierarchy is as follows:

`BaseBindingVMFragment` -------extends-------> `BaseVMFragment` -------extends-------> `BaseFragment`

---

### BaseVM...

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

---

### BaseBindingVM...

Everything that applies to `BaseVM...` applies to this class as well. A couple of things to add to this is that the Binding class is expecting a Generic (of the Binding-Layout). You'll also have to override a function `getLayout()` to finish the basic setup for DataBinding

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
