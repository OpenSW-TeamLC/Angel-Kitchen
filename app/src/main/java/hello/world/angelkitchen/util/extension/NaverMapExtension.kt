package hello.world.angelkitchen.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback

fun AppCompatActivity.setNaverMapRender(
    @IdRes containerId: Int,
    supportFragmentManager: FragmentManager,
    callback: OnMapReadyCallback
) {
    val mapFragment = supportFragmentManager.findFragmentById(containerId) as MapFragment?
        ?: MapFragment.newInstance().also {
            supportFragmentManager.beginTransaction().add(containerId, it).commit()
        }
    mapFragment.getMapAsync(callback)
}

fun Fragment.setNaverMapRender(
    @IdRes containerId: Int,
    supportFragmentManager: FragmentManager,
    callback: OnMapReadyCallback
) {
    val mapFragment = supportFragmentManager.findFragmentById(containerId) as MapFragment?
        ?: MapFragment.newInstance().also {
            supportFragmentManager.beginTransaction().add(containerId, it).commit()
        }
    mapFragment.getMapAsync(callback)
}

fun DialogFragment.setNaverMapRender(
    @IdRes containerId: Int,
    supportFragmentManager: FragmentManager,
    callback: OnMapReadyCallback
) {
    val mapFragment = supportFragmentManager.findFragmentById(containerId) as MapFragment?
        ?: MapFragment.newInstance().also {
            supportFragmentManager.beginTransaction().add(containerId, it).commit()
        }
    mapFragment.getMapAsync(callback)
}