package pl.applover.android.mvvmtest.util.ui.google_map

import android.annotation.SuppressLint
import android.graphics.Rect
import io.mironov.smuggler.AutoParcelable

/**
 * Created by Janusz Hain on 2018-02-02.
 */
@SuppressLint("ParcelCreator")
class GoogleMapPadding(private val rect: Rect, private val enum: DirectionsEnum? = null, private val bonusPadding: Int = 0) : AutoParcelable {

    var rectPadding: Rect


    enum class DirectionsEnum {
        MAP_LEFT_OF, MAP_TOP_OF, MAP_RIGHT_OF, MAP_BOTTOM_OF
    }

    init {
        when (enum) {
            DirectionsEnum.MAP_LEFT_OF -> {
                this.rectPadding = Rect(0, 0, rect.width() + bonusPadding, 0)
            }
            DirectionsEnum.MAP_TOP_OF -> {
                this.rectPadding = Rect(0, 0, 0, rect.height() + bonusPadding)
            }
            DirectionsEnum.MAP_RIGHT_OF -> {
                this.rectPadding = Rect(rect.width() + bonusPadding, 0, 0, 0)
            }
            DirectionsEnum.MAP_BOTTOM_OF -> {
                this.rectPadding = Rect(0, rect.height() + bonusPadding, 0, 0)
            }
            else -> {
                this.rectPadding = Rect(0 + bonusPadding, 0 + bonusPadding, 0 + bonusPadding, 0 + bonusPadding)
            }
        }
    }
}