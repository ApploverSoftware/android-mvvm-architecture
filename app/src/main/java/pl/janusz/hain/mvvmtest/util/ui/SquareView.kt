package pl.applover.dateanddrive.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup


/**
 * Created by Janusz Hain on 2017-07-11.
 *
 * This viewgroup makes views squares - max(width or height) : max(width or height)
 */

class SquareView : ViewGroup {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onLayout(changed: Boolean, l: Int, u: Int, r: Int, d: Int) {
        getChildAt(0).layout(0, 0, r - l, d - u) // Layout with max size
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val child = getChildAt(0)
        child.measure(widthMeasureSpec, widthMeasureSpec)
        val width = resolveSize(child.measuredWidth, widthMeasureSpec)
        child.measure(heightMeasureSpec, heightMeasureSpec)
        val height = resolveSize(child.measuredHeight, heightMeasureSpec)
        child.measure(width, height) // 2nd pass with the correct size

        if (width < height) {
            setMeasuredDimension(width, width)
        } else {
            setMeasuredDimension(height, height)
        }
    }

}