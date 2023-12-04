package be.appwise.util.extensions

fun String.capitalize(): String{
    return this.replaceFirstChar { char -> char.uppercaseChar() }
}