package hello.world.angelkitchen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AngelKitchenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}