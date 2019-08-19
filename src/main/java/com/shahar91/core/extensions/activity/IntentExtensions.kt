package com.shahar91.core.extensions.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap


/**
 * @see ICalendarItem item interface
 * other models can inherit from it and override the functions defined in the interface
 * Like this you can use different models in different places to start the google calendar intent with the
 * @see startIntentAddToCalendar(calenderItem : ICalendarItem) function.
 */

interface ICalendarItem {
    fun getBeginTime (): Long
    fun getEndTime (): Long
    fun getItemTitle (): String
    fun getItemDescription (): String?
    fun getEventLocation (): String?
}


//start the google calendar app and send a calendaritem with it
fun Activity.startIntentAddToCalendar(calendarItem: ICalendarItem?) {
    calendarItem?.let {

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, it.getBeginTime())
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, it.getEndTime())
            putExtra(CalendarContract.Events.TITLE, it.getItemTitle())
            putExtra(CalendarContract.Events.DESCRIPTION, it.getItemDescription())
            putExtra(CalendarContract.Events.EVENT_LOCATION, it.getEventLocation())
            /*putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")*/
        }
        startActivity(intent)
    }
}


/**
 * @see IMapsAddress item interface
 * other models can inherit from it and override the functions defined in the interface
 * Like this you can use different models in different places to start the google calendar intent with the
 * @see startIntentGoogleMaps function.
 */

interface IMapsAddress {
    var longitude : Double
    var latitude : Double
    var street: String
    var house_number: String?
    var city: String
    var country: String
    var postal_code: String

    fun getLocationString(queryIfNoCoordinates : String? = null) : String{
        return "google.navigation:q=" + if(longitude != null && latitude != null) "$latitude,$longitude" else  Uri.encode(queryIfNoCoordinates)
    }
}

/**
 * this function starts a google maps intent and you can pass on a object that inherits from the
 * @see IMapsAddress
 * @param latitude
 * @param longitude
 * @param queryIfNoCoordinates
 */
fun Activity.startIntentGoogleMaps(latitude: Double? = null, longitude: Double? = null, queryIfNoCoordinates: String? = "") {
    val locationString = "google.navigation:q=" + if(longitude != null && latitude != null) "$latitude,$longitude" else  Uri.encode(queryIfNoCoordinates)
    val gmmIntentUri = Uri.parse(locationString)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
    }
}

fun Activity.startIntentGoogleMaps(address: IMapsAddress) {
    val gmmIntentUri = Uri.parse(address.getLocationString())
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
    }
}


/**
 * @see startIntentOpenFileFromUrl
 * @param url this is the url of the file you want to open
 * With this function you can open a file with the right app if available
 */
fun Activity.startIntentOpenFileFromUrl(url: String?) {
    url?.let {
        try {
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url))
            if (mimeType != "text/plain") {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse(url), mimeType)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/viewer?url=$url")))
            }
        } catch (ex: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://docs.google.com/viewer?url=$url")))
        }

    }
}

/**
 * starts de app details intent of this app
 */
fun Activity.startIntentInstalledAppDetails() {
    if (baseContext == null) {
        return
    }
    val i = Intent()
    i.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    i.addCategory(Intent.CATEGORY_DEFAULT)
    i.data = Uri.parse("package:" + baseContext.packageName)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    baseContext.startActivity(i)
}

/**
 * starts the take image capture intent for result
 */
const val TAKE_PICTURE = 101
fun Activity.startIntentTakePhoto() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, TAKE_PICTURE)
}

/**
 * starts the pick photo intent
 */
const val PICK_IMAGE = 102
fun Activity.startIntentpickPhoto(title: String = "Select Image") {
    val getIntent = Intent(Intent.ACTION_GET_CONTENT)
    getIntent.type = "image/*"
    val chooserIntent = Intent.createChooser(getIntent, title)
    startActivityForResult(chooserIntent, PICK_IMAGE)
}


