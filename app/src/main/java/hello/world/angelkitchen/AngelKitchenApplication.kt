package hello.world.angelkitchen

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import hello.world.angelkitchen.util.PixelRatio
import hello.world.angelkitchen.util.PreferenceUtil

@HiltAndroidApp
class AngelKitchenApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        prefs = PreferenceUtil(this)
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var pixelRatio: PixelRatio
        lateinit var instance: AngelKitchenApplication
    }
}