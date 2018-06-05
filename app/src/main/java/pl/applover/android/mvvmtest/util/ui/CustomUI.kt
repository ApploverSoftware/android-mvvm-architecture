package pl.applover.enegivetest.util.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.nihaskalam.progressbuttonlibrary.CircularProgressButton
import pl.applover.enegivetest.util.extensions.delayed
import java.util.concurrent.TimeUnit


/**
 * Created by sp0rk on 06/06/17.
 */

class SquareRelativeLayout : RelativeLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (widthMeasureSpec < heightMeasureSpec) super.onMeasure(widthMeasureSpec, widthMeasureSpec) else super.onMeasure(heightMeasureSpec, heightMeasureSpec)
    }
}

class NonScrollableViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}

fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun ActionBar.toggleAnimations(state: Boolean) {
    try {
        javaClass.getDeclaredMethod("setShowHideAnimationEnabled", Boolean::class.javaPrimitiveType).invoke(this, state)
    } catch (exception: Exception) {
        try {
            val mActionBarField = javaClass.superclass.getDeclaredField("mActionBar")
            mActionBarField.isAccessible = true
            val icsActionBar = mActionBarField.get(this)
            val mShowHideAnimationEnabledField = icsActionBar.javaClass.getDeclaredField("mShowHideAnimationEnabled")
            mShowHideAnimationEnabledField.isAccessible = true
            mShowHideAnimationEnabledField.set(icsActionBar, state)
            if (!state) {
                val mCurrentShowAnimField = icsActionBar.javaClass.getDeclaredField("mCurrentShowAnim")
                mCurrentShowAnimField.isAccessible = true
                mCurrentShowAnimField.set(icsActionBar, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)

    drawable!!

    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

fun ImageView.changeAnimated(intoResId: Int) =
        changeAnimated(getBitmapFromVectorDrawable(context, intoResId))

fun ImageView.changeAnimated(into: Bitmap) {
    val anim_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    val anim_in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    anim_out.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            setImageBitmap(into)
            anim_in.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            startAnimation(anim_in)
        }
    })
    startAnimation(anim_out)
}

fun Drawable.toBitmap(): Bitmap {
    var width = intrinsicWidth
    width = if (width > 0) width else 1
    var height = intrinsicHeight
    height = if (height > 0) height else 1

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bitmap
}

fun CircularProgressButton.start(): Boolean {
    if (isProgress) {
        return false
    }
    showProgress()
    return true
}

@Suppress("UsePropertyAccessSyntax")
fun CircularProgressButton.stop(success: Boolean, resetTimeout: Long? = 1000, completion: (() -> Unit)? = null) {
    if (!isIdle) {
        val MORPH_DURATION_NORMAL = 450L
        TimeUnit.SECONDS.delayed(1) {
            if (success)
                showComplete()
            else
                showError()
            resetTimeout?.let {
                setClickable(false)
                Handler(Looper.getMainLooper()).postDelayed({
                    showIdle()
                    Handler(Looper.getMainLooper()).postDelayed({
                        setClickable(true)
                        completion?.invoke()
                    }, MORPH_DURATION_NORMAL)
                }, resetTimeout)
            } ?: setClickable(true)
        }
    }
}


@Suppress("UsePropertyAccessSyntax")
fun CircularProgressButton.stopAndStayComplete(success: Boolean, resetTimeout: Long? = 1000, completion: (() -> Unit)? = null, returnToComplete: Boolean = false) {
    val MORPH_DURATION_NORMAL = 450L
    TimeUnit.SECONDS.delayed(1) {
        if (success) {
            resetTimeout?.let {
                setClickable(false)
                Handler(Looper.getMainLooper()).postDelayed({
                    showComplete()
                    Handler(Looper.getMainLooper()).postDelayed({
                        setClickable(true)
                        completion?.invoke()
                    }, MORPH_DURATION_NORMAL)
                }, resetTimeout)
            }
        } else {
            showError()
            resetTimeout?.let {
                setClickable(false)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (returnToComplete) {
                        showComplete()
                    } else {
                        showIdle()
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        setClickable(true)
                        completion?.invoke()
                    }, MORPH_DURATION_NORMAL)
                }, resetTimeout)
            } ?: setClickable(true)
        }
    }
}

fun View.getRect(): Rect {
    val rect: Rect = Rect()
    getGlobalVisibleRect(rect)
    return rect
}

