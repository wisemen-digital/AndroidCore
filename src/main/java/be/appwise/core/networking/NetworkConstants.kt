package be.appwise.core.networking

object NetworkConstants {
    const val CLIENT_ID_KEY = "client_id"

    const val CLIENT_SECRET_KEY = "client_secret"

    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_PASSWORD_VALUE = "password" //Todo: grand type could be password
    const val GRANT_TYPE_CODE_VALUE = "code" //Todo: grand type could be code

    const val LOGIN_USER_KEY = "username" //Todo: this could be "email" or "username"

    const val HAWK_ACCESS_TOKEN_KEY = "access_token"
    const val REFRESH_TOKEN = "refresh_token"

    const val BEARER = "Bearer "

    const val HEADER_KEY_USER_AGENT = "User-Agent"
    const val HEADER_KEY_ACCEPT_LANGUAGE = "Accept-Language"
    const val HEADER_KEY_API_VERSION = "api-version"
    const val HEADER_KEY_APP_PLATFORM = "x-app-platform"
    const val ANDROID = "android"
    const val HEADER_KEY_APP_ID = "x-app-id"
    const val HEADER_KEY_AUTHORIZATION = "Authorization"
    const val HEADER_KEY_ACCEPT = "ACCEPT"
    const val HEADER_VALUE_ACCEPT_APPLICATION_JSON = "application/json"
}