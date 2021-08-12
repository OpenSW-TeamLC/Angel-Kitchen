package hello.world.angelkitchen.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue).toString()

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}