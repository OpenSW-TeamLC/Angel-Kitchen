package hello.world.angelkitchen.view.bottom_menu.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hello.world.angelkitchen.databinding.RecyclerRecordItemBinding

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.MyViewHolder>() {

    private var recordList: ArrayList<RecordData>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordAdapter.MyViewHolder {
        val binding = RecyclerRecordItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordAdapter.MyViewHolder, position: Int) {
        recordList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if(recordList == null) 0
        else recordList!!.size
    }

    fun setRecordData(recordData: ArrayList<RecordData>) {
        recordList = recordData
    }

    class MyViewHolder(binding: RecyclerRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val record = binding.tvRecord

        fun bind(recordData: RecordData) {
            record.text = recordData.record
        }
    }
}