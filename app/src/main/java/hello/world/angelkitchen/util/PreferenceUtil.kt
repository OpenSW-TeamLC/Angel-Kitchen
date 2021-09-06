package hello.world.angelkitchen.util

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceUtil @Inject constructor(
    application: Application
) {
    private val prefs: SharedPreferences =
        application.getSharedPreferences("prefs_name", Application.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue).toString()

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}