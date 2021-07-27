@file:Suppress("unused")

package be.appwise.core.extensions.activity

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
    fun getBeginTime(): Long
    fun getEndTime(): Long
    fun getItemTitle(): String
    fun getItemDescription(): String?
    fun getEventLocation(): String?
}

/**
 * This function starts an intent to a add an item to the calendar
 *
 * @param calendarItem Object derived from the ICalendarItem interface
 */
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
    var longitude: Double
    var latitude: Double
    var street: String
    var houseNumber: String?
    var city: String
    var country: String
    var postalCode: String

    fun getLocationString(queryIfNoCoordinates: String? = null): String {
        return "google.navigation:q=$latitude,$longitude"
        // longitude and latitude cannot be null...
        //        return "google.navigation:q=" + if(longitude != null && latitude != null) "$latitude,$longitude" else  Uri.encode(queryIfNoCoordinates)
    }
}

/**
 * This function starts a google maps intent using the longitude and latitude or a query if the previous are not available
 *
 * @param latitude The latitude coordinates of the point on the map
 * @param longitude The longitude coordinates of the point on the map
 * @param queryIfNoCoordinates Location query if no coordinates available
 */
fun Activity.startIntentGoogleMaps(latitude: Double? = null, longitude: Double? = null, queryIfNoCoordinates: String? = "") {
    /*
        //TODO: check and test
        //TODO: This could be used to let the user choose between google Maps and Waze

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + Uri.encode(queryIfNoCoordinates))
        startActivity(Intent.createChooser(intent, ""))
    */

    val locationString = "google.navigation:q=" + if (longitude != null && latitude != null) "$latitude,$longitude" else Uri.encode(queryIfNoCoordinates)
    val gmmIntentUri = Uri.parse(locationString)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
    }
}

/**
 * This function starts a google maps intent using an object which encapsulates all common address variables (i.e. city, longitude, country, ...)
 *
 * @see IMapsAddress
 * @param address Object derived from the IMapsAddress interface.
 */
fun Activity.startIntentGoogleMaps(address: IMapsAddress) {
    val gmmIntentUri = Uri.parse(address.getLocationString())
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
    }
}

/**
 * This function will open the app to make a call
 *
 * @param phoneNumber: The phoneNumber to make a call to
 */
fun Activity.startIntentTelephone(phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(Intent.createChooser(intent, "Select an app"))
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        snackBar("It seems like your device is unable to make any calls")
    }
}

/**
 * This function will open an app to send an email
 *
 * @param email: An email address to send a mail to
 */
fun Activity.startIntentMail(email: String) {
    try {
        val emails = arrayOf(email)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, emails)
        startActivity(Intent.createChooser(intent, "Select an app"))
    } catch (ex: Exception) {
        ex.printStackTrace()
        snackBar("It seems like your device is unable to make any calls")
    }
}

/**
 * With this function you can open a file with the right app if available
 *
 * @see startIntentOpenFileFromUrl
 * @param url this is the url of the file you want to open
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
 * This function will show the details/settings of this app
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

const val TAKE_PICTURE = 9999
/**
 * This function starts an intent with a result to capture an image.
 */
fun Activity.startIntentTakePhoto() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, TAKE_PICTURE)
}

const val PICK_IMAGE = 9998
/**
 * This function starts an intent with a result to select an
 * image from the device's storage.
 */
fun Activity.startIntentPickPhoto(title: String = "Select Image") {
    val getIntent = Intent(Intent.ACTION_GET_CONTENT)
    getIntent.type = "image/*"
    val chooserIntent = Intent.createChooser(getIntent, title)
    startActivityForResult(chooserIntent, PICK_IMAGE)
}