package com.wisemen.compose.foundation.example

import com.wisemen.compose.foundation.result.*
import com.wisemen.compose.foundation.extension.chunkedSafe
import com.wisemen.compose.foundation.extension.isValidEmail
import com.wisemen.compose.foundation.extension.secondOrNull
import com.wisemen.compose.foundation.extension.titleCaseFirstChar
import com.wisemen.compose.foundation.extension.truncate
import com.wisemen.compose.foundation.logger.logE
import com.wisemen.compose.foundation.logger.logI
import com.wisemen.compose.foundation.result.NetworkResult
import com.wisemen.compose.foundation.util.PlatformUtils
import com.wisemen.compose.foundation.util.getCurrentPlatform

class CoreFoundationExample {

    fun demonstrateStringExtensions() {
        val email = "test@example.com"
        val isValid = email.isValidEmail()
        logI("Email validation result: $isValid")

        val text = "hello world".titleCaseFirstChar()
        logI("Title case: $text")

        val truncated = "This is a very long text".truncate(10)
        logI("Truncated: $truncated")
    }

    fun demonstrateCollectionExtensions() {
        val list = listOf(1, 2, 3, 4, 5)
        val second = list.secondOrNull()
        logI("Second element: $second")

        val chunked = list.chunkedSafe(2)
        logI("Chunked: $chunked")
    }

    fun demonstrateResourcePattern() {
        // Success example
        val successResource = Resource.success("Data loaded successfully")
        successResource.onSuccess { data ->
            logI("Success: $data")
        }

        // Error example
        val errorResource = Resource.error(Exception("Network error"), "Failed to load data")
        errorResource.onError { exception, message ->
            logE("Error: $message", exception)
        }

        // Loading example
        val loadingResource = Resource.loading()
        if (loadingResource.isLoading) {
            logI("Currently loading...")
        }
    }

    fun demonstrateNetworkResult() {
        val networkSuccess = NetworkResult.success("API response")
        val networkError = NetworkResult.unauthorized()
        val networkLoading = NetworkResult.loading()

        networkSuccess.onSuccess { data ->
            logI("Network success: $data")
        }

        networkError.onError { code, message, exception ->
            logE("Network error [$code]: $message", exception)
        }
    }

    fun demonstratePlatformUtils() {
        val platform = getCurrentPlatform()
        val platformName = PlatformUtils.platformName
        val isDebug = PlatformUtils.isDebug
        val uuid = PlatformUtils.randomUUID()

        logI("Platform: $platform")
        logI("Platform name: $platformName")
        logI("Is debug: $isDebug")
        logI("Generated UUID: $uuid")
    }

    fun runAllExamples() {
        logI("=== Core Foundation Examples ===")
        demonstrateStringExtensions()
        demonstrateCollectionExtensions()
        demonstrateResourcePattern()
        demonstrateNetworkResult()
        demonstratePlatformUtils()
        logI("=== Examples Complete ===")
    }
}