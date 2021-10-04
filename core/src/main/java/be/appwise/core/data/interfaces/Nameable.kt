package be.appwise.core.data.interfaces

interface Nameable {
    var first_name: String?
    var last_name: String?

    fun getFullName(): String = "$first_name $last_name".trim().replace("null", "")
    fun getShortenedName(): String = "${first_name?.first()}. $last_name".trim().replace("null", "")
    fun getInitials(): String =
        "${first_name?.first()?.uppercase()}${last_name?.first()?.uppercase()}".replace("null", "")
}