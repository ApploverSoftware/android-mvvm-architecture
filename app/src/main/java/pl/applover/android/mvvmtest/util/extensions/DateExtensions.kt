package pl.applover.android.mvvmtest.util.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Janusz Hain on 2018-01-08.
 */

fun Calendar.setStartOfTheDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    return this
}

fun Calendar.setEndOfTheDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    return this
}

fun Date.getDifference(dateToBeCompared: Date): Long {
    val time = time
    val comparedTime = dateToBeCompared.time
    if (comparedTime > time) {
        return comparedTime - time
    } else {
        return time - comparedTime
    }
}

/**
 * <pre>
 * Returns Date object if parsing is okay, otherwise null
</pre> *
 */
fun stringToDate(dateString: String?, simpleDateFormat: SimpleDateFormat): Date? {
    if (dateString == null) {
        return null
    }
    return try {
        simpleDateFormat.parse(dateString)
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }

}

fun dateToString(date: Date?, simpleDateFormat: SimpleDateFormat): String? {
    if (date == null) return null
    return simpleDateFormat.format(date)
}

fun rubyOnRailsFormat(): SimpleDateFormat {
    //2017-11-16T00:00:00.000Z
    val format = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    val sdf = SimpleDateFormat(format)
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf
}

@SuppressLint("SimpleDateFormat")
fun polishDataFormat(longDate: Boolean = false): SimpleDateFormat {
    if (longDate) {
        val format = "EEE, dd.MM.yyyy HH:mm"
        return SimpleDateFormat(format, getPolishLocale())
    } else {
        val format = "dd.MM.yyyy HH:mm"
        return SimpleDateFormat(format)
    }
}

fun getDefaultLocal(): Locale {
    return java.util.Locale.getDefault()
}

fun getLocalFormat(locale: Locale): DateFormat {
    return DateFormat.getDateInstance(DateFormat.LONG, locale)
}

fun formatElapsedTime(millis: Long): String {
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
}

@SuppressLint("SimpleDateFormat")
fun hoursMinutesFormat(): SimpleDateFormat {
    val format = "HH:mm"
    return SimpleDateFormat(format)
}

fun getPolishLocale(): Locale {
    return Locale("pl", "PL")
}