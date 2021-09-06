package hello.world.angelkitchen.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

fun AppCompatActivity.replace(
    @IdRes frameId: Int,
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .replace(frameId, fragment, null)
        .commit()
}

fun AppCompatActivity.replaceToBackStack(
    @IdRes frameId: Int,
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .replace(frameId, fragment, null)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.add(
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .add(fragment, null)
        .commit()
}