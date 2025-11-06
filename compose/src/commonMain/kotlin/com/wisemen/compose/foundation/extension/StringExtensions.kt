package com.wisemen.compose.foundation.extension

fun String.titleCaseFirstChar(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase() else it.toString()
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(this)
}

fun String.truncate(maxLength: Int, suffix: String = "..."): String {
    return if (length <= maxLength) this else take(maxLength - suffix.length) + suffix
}

fun String.removeWhitespace(): String = replace("\\s".toRegex(), "")

fun String.toSlug(): String = lowercase()
    .replace("[^a-z0-9\\s-]".toRegex(), "")
    .replace("\\s+".toRegex(), "-")
    .trim('-')

fun String?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()

fun String?.isNullOrBlank(): Boolean = this == null || this.isBlank()

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}