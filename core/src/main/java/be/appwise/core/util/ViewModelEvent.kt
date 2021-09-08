package be.appwise.core.util

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 * https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 */
open class ViewModelEvent<out T>(private val content: T? = null) {
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T? = content
}