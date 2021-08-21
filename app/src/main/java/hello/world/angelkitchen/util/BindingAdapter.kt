package hello.world.angelkitchen.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import hello.world.angelkitchen.R
import hello.world.angelkitchen.view.bottom_menu.search.RecordAdapter
import hello.world.angelkitchen.view.bottom_menu.search.RecordData
import hello.world.angelkitchen.view.bottom_menu.search.SearchViewModel

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }
}