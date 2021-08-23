package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import hello.world.angelkitchen.R
import hello.world.angelkitchen.databinding.RecyclerSearchResultItemBinding

class SearchResultAdapter(
    private var resultList: List<SearchResultData>,
    val onClickItem: (searchResultData: SearchResultData) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerSearchResultItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(resultList[position], onClickItem)
    }

    override fun getItemCount(): Int = resultList.size

    fun setData(newData: List<SearchResultData>) {
        resultList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerSearchResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            searchResultData: SearchResultData,
            onClickItem: (searchResultData: SearchResultData) -> Unit
        ) {
            binding.tvPlace.text = searchResultData.place
            binding.tvAddress.text = searchResultData.address
            binding.tvPhone.text = searchResultData.number
            binding.ivThumb.load(searchResultData.imgPath) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
            }
            binding.root.setOnClickListener {
                onClickItem.invoke(searchResultData)
            }
        }
    }
}