package hello.world.angelkitchen

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import hello.world.angelkitchen.util.PixelRatio
import hello.world.angelkitchen.util.PreferenceUtil

@HiltAndroidApp
class AngelKitchenDevelopApplication : Application() {
    override fun onCreate() {
        prefs = PreferenceUtil(this)
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var pixelRatio: PixelRatio
    }
}