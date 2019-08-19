package com.shahar91.core.networking

object NetworkConstants {
    const val CLIENT_ID_KEY = "client_id"

    const val CLIENT_SECRET_KEY = "client_secret"

    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_PASSWORD_VALUE = "password" //Todo: grand type could be password
    const val GRANT_TYPE_CODE_VALUE = "code" //Todo: grand type could be code

    const val LOGIN_USER_KEY = "username" //Todo: this could be "email" or "username"

    const val HAWK_ACCESS_TOKEN_KEY = "access_token"
    const val REFRESH_TOKEN = "refresh_token"

    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "

    const val USER_AGENT = "User-Agent"
    const val ACCEPT_LANGUAGE = "Accept-Language"
    const val API_VERSION = "api-version"
    const val APP_PLATFORM = "x-app-platform"
    const val ANDROID = "android"
    const val APP_ID = "x-app-id"
    const val HEADER_AUTHORIZATION_KEY = "Authorization"
    const val ACCEPT = "ACCEPT"
    const val ACCEPT_APPLICATION_JSON_VALUE = "application/json"

    const val HAWK_CURENT_USER_ID = "hawk_current_user_id"
}