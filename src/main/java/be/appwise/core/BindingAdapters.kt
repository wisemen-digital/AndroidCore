@file:Suppress("unused")

package be.appwise.core

import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import be.appwise.core.extensions.libraries.*
import be.appwise.core.extensions.view.setHtmlText
import com.bumptech.glide.Glide
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import java.io.File
import java.util.*

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("enabledIf")
fun enabledIf(view: View, enabled: Boolean) {
    view.isEnabled = enabled
    view.isClickable = enabled
    view.isFocusable = enabled
}


@BindingAdapter("app:glideSrc")
fun glideSrc(view: ImageView, drawable: Int) {
    Glide.with(view)
        .load(drawable)
        .into(view)
}

@BindingAdapter("app:glideLoadUrl")
fun glideLoadUrl(view: ImageView, url: String?) {
    view.load(url)
}

@BindingAdapter("app:glideLoadFile")
fun glideLoadFile(view: ImageView, file: File?) {
    view.loadFile(file)
}

@BindingAdapter("app:glideLoadUrlCenterCrop")
fun glideLoadUrlCenterCrop(view: ImageView, url: String?) {
    view.loadCenterCrop(url)
}

@BindingAdapter("app:glideLoadCircleUrl")
fun glideLoadCircleUrl(view: ImageView, url: String?) {
    view.loadCircle(url)
}

@BindingAdapter("app:glideLoadRoundedUrl")
fun glideLoadRoundedCornersUrl(view: ImageView, url: String?) {
    view.loadRoundedCorners(url)
}


@BindingAdapter("app:relativeDateTimeText")
fun setRelativeDateTimeText(view: TextView, timeInMillis: Long?) {
    // minimum amount is hours, so minutes and seconds will be shown as non-relative date
    timeInMillis?.let {
        view.text = DateUtils.getRelativeDateTimeString(view.context, it * 1000, DateUtils.DAY_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE)
    }
}

@BindingAdapter("app:relativeDateText")
fun setRelativeDateText(view: TextView, timeInMillis: Long?) {
    // minimum amount is hours, so minutes and seconds will be shown as non-relative date
    timeInMillis?.let {
        val now = System.currentTimeMillis()
        val time = it * 1000
        val days = Days.daysBetween(DateTime(now).toLocalDate(), DateTime(time).toLocalDate())
            .days
        if (days in -2..2) view.text = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS)
        else getDateText(view, it * 1000)
    }
}

@BindingAdapter(value = ["timestamp", "showHours"], requireAll = false)
fun setDateText(view: TextView, timeStamp: Long, onlyDate: Boolean = false) {
    if (onlyDate) {
        setRelativeDateText(view, timeStamp)
    } else {
        setRelativeDateTimeText(view, timeStamp)
    }
}

@BindingAdapter("app:dateTimeText")
fun getDateTimeText(view: TextView, timeInMillis: Long) {
    val format = DateTimeFormat.shortDateTime()
        .withZone(DateTimeZone.getDefault())
    view.text = format.print(timeInMillis.times(1000))
}


@BindingAdapter("app:dateText")
fun getDateText(view: TextView, timeInMillis: Long?) {
    // minimum amount is hours, so minutes and seconds will be shown as non-relative date
    timeInMillis?.let {
        val format = DateFormat.getDateFormat(view.context)
        format.timeZone = TimeZone.getDefault()
        view.text = format.format(it)
    }
}

@BindingAdapter("day")
fun setDayString(textView: TextView, date: Long) {
    textView.text = DateFormat.format("dd", date * 1000)
}

@BindingAdapter("blockBackground")
fun setBlockBackground(imageView: ImageView, color: Int) {
    imageView.setBackgroundColor(color)
}

@BindingAdapter("htmlText")
fun setHtmlText(textView: TextView, text: String?) {
    textView.setHtmlText(text)
}

@BindingAdapter("htmlText")
fun setHtmlText(textView: TextView, stringId: Int) {
    setHtmlText(textView, textView.context.getString(stringId))
}

@BindingAdapter("month")
fun setMonthString(textView: TextView, date: Long) {
    textView.text = DateFormat.format("MMM", date * 1000)
    textView.text = DateFormat.format("MMM", date * 1000)
}


//@BindingAdapter("isFavorite")
//fun setFavoriteIcon(imageView: ImageView, isFavorite: Boolean) {
//    imageView.setImageResource(if (isFavorite) R.drawable.ic_make_favorite_enabled else R.drawable.ic_make_favorite)
//}

//@BindingAdapter("gender")
//fun setGenderIcon(textView: TextView, gender: String?) {
//    val drawable = ContextCompat.getDrawable(textView.context,
//        if (gender == Profile.GENDER_MALE)
//            R.drawable.ic_gender_male
//        else
//            R.drawable.ic_gender_female)
//    textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
//}
//
//@BindingAdapter("dob")
//fun setAge(textView: TextView, dob: Long) {
//    val age = (dob * 1000).getAge()
//    textView.text = textView.context.getString(R.string.profile_text_age_years, age)
//}
//
//@BindingAdapter("newsRead")
//fun setNewsRead(textView: AppCompatTextView, read: Boolean) {
//    textView.text = textView.context.getString(if (read) R.string.news_text_read else R.string.news_text_unread)
//    textView.setCompoundDrawablesWithIntrinsicBounds(if (read) R.drawable.ic_read else R.drawable.ic_circle_read, 0, 0, 0)
//    textView.compoundDrawablePadding = 10
//}
//
//
//@BindingAdapter("eventRange")
//fun setEventRange(textView: TextView, event: Event?) {
//    event?.let {
//        val hourFormat = DateTimeFormat.shortDateTime()
//        val startHour = hourFormat.print(it.start_event * 1000)
//        val endHour = hourFormat.print(it.end_event * 1000)
//        textView.text = textView.context.getString(R.string.event_text_date_range, startHour, endHour)
//    }
//}
//
//@BindingAdapter(value = ["rangeStart", "rangeEnd", "isDate"], requireAll = false)
//fun setDateRange(textView: TextView, start: Long, end: Long, isDate: Boolean = false) {
//    textView.setDateRange(start, end, isDate)
//}
//
//@BindingAdapter(value = ["rangeStartHour", "rangeEndHour"])
//fun setDateHourRange(textView: TextView, start: Long, end: Long) {
//    val timeFormat = DateFormat.getTimeFormat(textView.context)
//    val startTime = timeFormat.format(start * 1000)
//    val endTime = timeFormat.format(end * 1000)
//    textView.text = textView.context.getString(R.string.event_text_date_range, startTime, endTime)
//}
//
//@BindingAdapter(value = ["startHour", "endHour"])
//fun setHourAndDuration(textView: TextView, startDate: Long, endDate: Long) {
//    val timeFormat = DateFormat.getTimeFormat(textView.context)
//    val startTime = timeFormat.format(startDate * 1000)
//    val duration = (endDate - startDate) / 60
//    textView.text = App.getContext()
//        .getString(R.string.terrain_reservation_text_minutes, startTime, duration)
//}