package com.wisemen.compose.foundation.extension

fun <T> List<T>.safeGet(index: Int): T? = if (index in indices) this[index] else null

fun <T> List<T>.secondOrNull(): T? = safeGet(1)

fun <T> List<T>.penultimate(): T? = safeGet(size - 2)

fun <T> MutableList<T>.addIfNotExists(item: T): Boolean {
    return if (!contains(item)) {
        add(item)
        true
    } else false
}

fun <T> List<T>.chunkedSafe(size: Int): List<List<T>> {
    return if (size <= 0) listOf(this) else chunked(size)
}

fun <T> Collection<T>.isNotEmpty(): Boolean = !isEmpty()

fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: V): V = this[key] ?: defaultValue

fun <T> Set<T>.toggle(item: T): Set<T> {
    return if (contains(item)) this - item else this + item
}