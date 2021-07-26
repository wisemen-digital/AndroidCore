package be.appwise.networking.util

import be.appwise.core.util.HawkValueDelegate
import be.appwise.networking.model.AccessToken

internal object HawkUtils {
    private const val HAWK_ACCESS_TOKEN_KEY = "access_token"
    var hawkAccessToken : AccessToken? by HawkValueDelegate(HAWK_ACCESS_TOKEN_KEY, null)
}