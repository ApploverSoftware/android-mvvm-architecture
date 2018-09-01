package pl.applover.architecture.mvvm.util.extensions

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View


/**
 * Created by Janusz Hain on 2018-01-23.
 */

/**
 * Makes first occurrence of [text] clickable. To color it, use [clickableSpan.updateDrawState]
 */
fun SpannableString.setClickableSpannable(clickableSpan: ClickableSpan, text: String) {

    val startIndex = indexOf(text)
    val endIndex = startIndex + text.length

    setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
}

/**
 * Don't forget to set textView.movementMethod = LinkMovementMethod.getInstance() !!
 * And this one can be nice too: textView.highlightColor = Color.TRANSPARENT
 */
fun createUnderlineText(text: String, textColorRid: Int, clickCallback: (() -> Unit)? = null): SpannableString {
    val spannableString = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View?) {
            clickCallback?.invoke()
        }

        override fun updateDrawState(ds: TextPaint?) {
            super.updateDrawState(ds)
            ds?.color = getColor(textColorRid)
            ds?.isUnderlineText = true
        }
    }
    spannableString.setSpan(clickableSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    return spannableString
}

fun doubleToTwoDigitsFormat(value: Double?) = if (value != null) "%.2f".format(value) else null

fun doubleToOneDigitFormat(value: Double?) = if (value != null) "%.1f".format(value) else null