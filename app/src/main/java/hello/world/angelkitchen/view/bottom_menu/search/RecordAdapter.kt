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
        val selectItem = recordList[position]
        holder.binding.tvRecord.text = selectItem.recordText
        if(selectItem.isClicked) {
            holder.binding.tvRecord.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTypeface(null, Typeface.ITALIC)
            }
        } else {
            holder.binding.tvRecord.apply {
                paintFlags = 0
                setTypeface(null, Typeface.NORMAL)
            }
        }

        holder.binding.ivRemove.setOnClickListener {
            onClickDelete.invoke(selectItem)
        }
        holder.binding.root.setOnClickListener {
            onClickItem.invoke(selectItem)
        }
    }

    override fun getItemCount(): Int = recordList.size

    class MyViewHolder(val binding: RecyclerRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}