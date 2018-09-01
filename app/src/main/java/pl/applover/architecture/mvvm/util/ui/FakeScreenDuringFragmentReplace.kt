package pl.applover.architecture.mvvm.util.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * Created by Janusz Hain on 2018-02-06.
 */

//This is hack for replacing fragments to avoid "flashing" of nested fragments disappearing
/*

private var b: Bitmap? = null

override fun onPause() {
    b = loadBitmapFromView(view!!);
    super.onPause()
}

override fun onDestroyView() {
    val bd = BitmapDrawable(context.resources, b)
    fragmentMapContainer.background = bd
    b = null
    super.onDestroyView()
}
        */

fun loadBitmapFromView(v: View): Bitmap {
    val b = Bitmap.createBitmap(v.width,
            v.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    v.layout(0, 0, v.width,
            v.height)
    v.draw(c)
    return b
}