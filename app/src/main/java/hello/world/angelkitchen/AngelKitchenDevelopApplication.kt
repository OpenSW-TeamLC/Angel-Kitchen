package hello.world.angelkitchen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hello.world.angelkitchen.util.PixelRatio

@HiltAndroidApp
class AngelKitchenDevelopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var pixelRatio: PixelRatio
    }
}