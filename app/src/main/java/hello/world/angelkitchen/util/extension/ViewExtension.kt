package hello.world.angelkitchen.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

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

fun AppCompatActivity.replaceAdd(
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .add(fragment, null)
        .commit()
}