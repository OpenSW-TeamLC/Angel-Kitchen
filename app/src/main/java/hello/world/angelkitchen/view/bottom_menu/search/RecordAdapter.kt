package hello.world.angelkitchen.view.bottom_menu.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hello.world.angelkitchen.database.search_fragment.SearchFragmentEntity
import hello.world.angelkitchen.databinding.RecyclerRecordItemBinding

class RecordAdapter(
    private var recordList: List<SearchFragmentEntity>,
    private val onClickDelete: (recordData: SearchFragmentEntity) -> Unit,
    private val onClickItem: (recordData: SearchFragmentEntity) -> Unit
) : RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RecyclerRecordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recordList[position],
            onClickDelete,
            onClickItem
        )
    }

    override fun getItemCount(): Int = recordList.size

    fun setData(newData : List<SearchFragmentEntity>) {
        recordList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(
                recordData: SearchFragmentEntity,
                onClickDelete: (recordData: SearchFragmentEntity) -> Unit,
                onClickItem: (recordData: SearchFragmentEntity) -> Unit
            ) {
                binding.tvRecord.text = recordData.preSearchWord

                binding.ivRemove.setOnClickListener {
                    onClickDelete.invoke(recordData)
                }
                binding.root.setOnClickListener {
                    onClickItem.invoke(recordData)
                }
            }
        }
}