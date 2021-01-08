package be.appwise.core.networking.model

import com.google.gson.JsonArray

class PaginationObject(val data: JsonArray, val pagination: Pagination) {
    inner class Pagination(var total : Int ,var count : Int ,var per_page : Int = 10,var current_page : Int ,var total_pages : Int, var links : Links?)
    inner class Links(var next: String?,var previous: String?)
}
