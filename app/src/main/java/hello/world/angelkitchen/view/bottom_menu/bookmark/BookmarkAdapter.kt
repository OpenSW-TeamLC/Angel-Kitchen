package hello.world.angelkitchen.view.bottom_menu.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import hello.world.angelkitchen.AngelKitchenDevelopApplication
import hello.world.angelkitchen.R
import hello.world.angelkitchen.databinding.RecyclerBookmarkItemBinding
import hello.world.angelkitchen.view.bottom_menu.search.RecordData

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
        val selectItem = bookmarkList[position]
        holder.binding.tvPlace.text = selectItem.place
        holder.binding.tvAddress.text = selectItem.address
        holder.binding.tvPhone.text = selectItem.number
        holder.binding.ivThumb.load(selectItem.imgPath) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }

        holder.binding.btnDirection.setOnClickListener {
            onClickButton.invoke(selectItem)
        }
        holder.binding.root.setOnClickListener {
            onClickItem.invoke(selectItem)
        }
    }

    override fun getItemCount(): Int = bookmarkList.size

    fun setData(newData: List<BookmarkData>) {
        bookmarkList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerBookmarkItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}