package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pdam.databinding.ItemFotoMeterNotFinishedBinding
import com.codelabs.pdam.model.DetailReadMeterResponse

class PhotoMeterNFAdapter (val data: ArrayList<DetailReadMeterResponse.Data?> = arrayListOf()) :
    RecyclerView.Adapter<PhotoMeterNFAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFotoMeterNotFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PhotoMeterNFAdapter.ViewHolder {
        val binding =
            ItemFotoMeterNotFinishedBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoMeterNFAdapter.ViewHolder, position: Int) {
        val result = data[position]
        val image = holder.binding.fotoOrder

        Glide.with(holder.itemView.context)
            .load(result?.imagePathM)
            .into(image)

        holder.binding.deleteFoto.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setFotoMet(datas: List<DetailReadMeterResponse.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}