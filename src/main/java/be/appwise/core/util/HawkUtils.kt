package be.appwise.core.util

import be.appwise.core.networking.AccessToken

internal object HawkUtils {
    private const val HAWK_ACCESS_TOKEN_KEY = "access_token"
    var hawkAccessToken : AccessToken? by HawkValueDelegate(HAWK_ACCESS_TOKEN_KEY, null)
}