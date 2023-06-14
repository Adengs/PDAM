package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pdam.databinding.ItemFotoMeterFinishedBinding
import com.codelabs.pdam.databinding.ItemFotoMeterNotFinishedBinding
import com.codelabs.pdam.model.DetailReadMeterResponse

class PhotoMeterFAdapter (val data: ArrayList<String?> = arrayListOf()) :
    RecyclerView.Adapter<PhotoMeterFAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFotoMeterFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFotoMeterFinishedBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        val image = holder.binding.fotoOrder

        Glide.with(holder.itemView.context)
            .load(result)
            .into(image)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setFotoMet(datas: List<String?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}