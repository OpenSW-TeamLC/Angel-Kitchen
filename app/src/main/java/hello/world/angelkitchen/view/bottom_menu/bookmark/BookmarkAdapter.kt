package hello.world.angelkitchen.view.bottom_menu.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import hello.world.angelkitchen.R
import hello.world.angelkitchen.databinding.RecyclerBookmarkItemBinding

class BookmarkAdapter(
    private var bookmarkList: List<BookmarkData>,
    val onClickItem: (bookmarkData: BookmarkData) -> Unit,
    val onClickButton: (bookmarkData: BookmarkData) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RecyclerBookmarkItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookmarkList[position], onClickItem, onClickButton)
    }

    override fun getItemCount(): Int = bookmarkList.size

    fun setData(newData: List<BookmarkData>) {
        bookmarkList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerBookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            bookmarkData: BookmarkData,
            onClickItem: (bookmarkData: BookmarkData) -> Unit,
            onClickButton: (bookmarkData: BookmarkData) -> Unit
        ) {
            binding.tvPlace.text = bookmarkData.place
            binding.tvAddress.text = bookmarkData.address
            binding.tvPhone.text = bookmarkData.number
            binding.ivThumb.load(bookmarkData.imgPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
            }

            binding.btnDirection.setOnClickListener {
                onClickButton.invoke(bookmarkData)
            }
            binding.root.setOnClickListener {
                onClickItem.invoke(bookmarkData)
            }
        }
    }
}