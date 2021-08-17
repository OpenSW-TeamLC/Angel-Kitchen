package hello.world.angelkitchen.view.bottom_menu.search

import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hello.world.angelkitchen.databinding.RecyclerRecordItemBinding

class RecordAdapter(
    private var recordList: List<RecordData>,
    val onClickDelete: (recordData: RecordData) -> Unit,
    val onClickItem: (recordData: RecordData) -> Unit
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
        holder.bind(recordList[position], onClickDelete, onClickItem)
    }

    override fun getItemCount(): Int = recordList.size

    fun setData(newData : List<RecordData>) {
        recordList = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(
                recordData: RecordData,
                onClickDelete: (recordData: RecordData) -> Unit,
                onClickItem: (recordData: RecordData) -> Unit
            ) {
                binding.tvRecord.text = recordData.recordText
                if(recordData.isClicked) {
                    binding.tvRecord.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTypeface(null, Typeface.ITALIC)
                    }
                } else {
                    binding.tvRecord.apply {
                        paintFlags = 0
                        setTypeface(null, Typeface.NORMAL)
                    }
                }

                binding.ivRemove.setOnClickListener {
                    onClickDelete.invoke(recordData)
                }
                binding.root.setOnClickListener {
                    onClickItem.invoke(recordData)
                }
            }
        }
}