## <u>Initialize</u>

In order to initialize AndroidCore correctly you have 2 options.

- CoreApp: to initialze the Logger, ErrorActivity and Hawk (Hawk is initialized by default by the `init()` on `CoreApp`

```kotlin
CoreApp.init(this)
    .initializeErrorActivity(BuildConfig.DEBUG)
    .initializeLogger()
    .build()
```

Do note, that when you use AndroidCore as a Dependency that the `isLoggable` parameter in `initializeLogger()` should be added. It is best to use `BuildConfig.DEBUG` as the parameter so the logs won't show up in a production build.

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
