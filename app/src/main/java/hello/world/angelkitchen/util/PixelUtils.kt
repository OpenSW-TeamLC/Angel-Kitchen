package hello.world.angelkitchen.util

import android.app.Application
import androidx.annotation.Px
import hello.world.angelkitchen.AngelKitchenApplication
import javax.inject.Inject
import kotlin.math.roundToInt

class PixelRatio @Inject constructor(
    private val application: Application
) {
    private val displayMetrics
        get() = application.resources.displayMetrics

    val screenWidth
        get() = displayMetrics.widthPixels

    val screenHeight
        get() = displayMetrics.heightPixels

    val screenShort
        get() = screenWidth.coerceAtMost(screenHeight)

    val screenLong
        get() = screenWidth.coerceAtLeast(screenHeight)

    @Px
    fun toPixel(dp: Int) = (dp * displayMetrics.density).roundToInt()

    fun toDP(@Px pixel: Int) = (pixel / displayMetrics.density).roundToInt()
}

val Number.pixel: Int
    @Px get() = AngelKitchenApplication.pixelRatio.toDP(this.toInt())

val Number.dp: Int
    get() = AngelKitchenApplication.pixelRatio.toPixel(this.toInt())

val Number.pixelFloat: Float
    @Px get() = AngelKitchenApplication.pixelRatio.toDP(this.toInt()).toFloat()

val Number.dpFloat: Float
    get() = AngelKitchenApplication.pixelRatio.toPixel(this.toInt()).toFloat()