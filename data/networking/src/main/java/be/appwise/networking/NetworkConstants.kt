package be.appwise.networking

object NetworkConstants {
    const val KEY_CLIENT_ID = "client_id"
    const val KEY_CLIENT_SECRET = "client_secret"

    const val KEY_GRANT_TYPE = "grant_type"
    const val VALUE_GRANT_TYPE_CODE = "code"
    const val VALUE_GRANT_TYPE_PASSWORD = "password"

    const val FIELD_IDENTIFIER_REFRESH_TOKEN = "refresh_token"

    const val HEADER_KEY_ACCEPT = "ACCEPT"
    const val HEADER_KEY_ACCEPT_LANGUAGE = "Accept-Language"
    const val HEADER_KEY_API_VERSION = "api-version"
    const val HEADER_KEY_APP_PLATFORM = "x-app-platform"
    const val HEADER_KEY_APP_ID = "x-app-id"
    const val HEADER_KEY_AUTHORIZATION = "Authorization"
    const val HEADER_KEY_USER_AGENT = "User-Agent"

    const val HEADER_VALUE_ACCEPT_APPLICATION_JSON = "application/json"
    const val HEADER_VALUE_PLATFORM_ANDROID = "android"

    const val BAGEL_INTERCEPTOR_DEVICE_BLUETOOTH_NAME = "bluetooth_name"
    const val BAGEL_INTERCEPTOR_DEVICE_NAME = "device_name"
}