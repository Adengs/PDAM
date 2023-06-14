package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pdam.databinding.ItemFotoLocationFinishedBinding
import com.codelabs.pdam.databinding.ItemFotoLocationNotFinishedBinding
import com.codelabs.pdam.model.DetailReadMeterResponse

class PhotoLocationFAdapter (val data: ArrayList<String?> = arrayListOf()) :
    RecyclerView.Adapter<PhotoLocationFAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemFotoLocationFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFotoLocationFinishedBinding.inflate(LayoutInflater.from(parent.context),
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

    fun setFotoLoc(datas: List<String?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}