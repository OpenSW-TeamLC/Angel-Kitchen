package hello.world.angelkitchen.util

import android.app.Application
import androidx.annotation.Px
import hello.world.angelkitchen.AngelKitchenDevelopApplication
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
    @Px get() = AngelKitchenDevelopApplication.pixelRatio.toDP(this.toInt())

val Number.dp: Int
    get() = AngelKitchenDevelopApplication.pixelRatio.toPixel(this.toInt())

val Number.pixelFloat: Float
    @Px get() = AngelKitchenDevelopApplication.pixelRatio.toDP(this.toInt()).toFloat()

val Number.dpFloat: Float
    get() = AngelKitchenDevelopApplication.pixelRatio.toPixel(this.toInt()).toFloat()