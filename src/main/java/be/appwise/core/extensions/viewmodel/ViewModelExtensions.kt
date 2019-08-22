package be.appwise.core.extensions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * create a factory for a ViewModel with 1 argument
 * @param constructor of ViewModel
 */
fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}

/**
 * create a factory for a ViewModel with 2 arguments
 * @param constructor of ViewModel
 */
fun <T : ViewModel, A, B> doubleArgsViewModelFactory(constructor: (A, B) -> T):
            (A, B) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A , arg2: B ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg, arg2) as V
            }
        }
    }
}

/**
 * create a factory for a ViewModel with 3 arguments
 * @param constructor of ViewModel
 */
fun <T : ViewModel, A, B, C> tripleArgsViewModelFactory(constructor: (A, B, C) -> T):
            (A, B, C) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A , arg2: B, arg3: C ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg, arg2, arg3) as V
            }
        }
    }
}