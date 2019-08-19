@file:Suppress("unused")

package com.shahar91.core.extensions.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*


fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
    return GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) { block() }
}

suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.Default, CoroutineStart.DEFAULT) { block() }
}

suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
    return async(block).await()
}

class UiLifecycleScope : CoroutineScope, LifecycleObserver {

    private var job: Job = Job()
    override val coroutineContext
        get() = job + Dispatchers.Main

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun destroy() = job.cancel()
}

