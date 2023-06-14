package com.codelabs.pdam.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pdam.databinding.ItemFinishedBinding
import com.codelabs.pdam.databinding.ItemLastHistoryBinding
import com.codelabs.pdam.model.ReadMeterResponse

class LastHistoryAdapter (val data: ArrayList<ReadMeterResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<LastHistoryAdapter.ViewHolder>() {
    var onClick: ((ReadMeterResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemLastHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLastHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        val img = holder.binding.photoLocation

        Glide.with(holder.itemView.context)
            .load(result?.photoLocation)
            .into(img)
        holder.binding.nameCust.text = result?.customer?.name.toString()
        holder.binding.location.text = Html.fromHtml(result?.customer?.address.toString().replace("<p>","").replace("<br>",""))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataLastHistory(datas: List<ReadMeterResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}