package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codelabs.pdam.databinding.ItemFotoLocationNotFinishedBinding
import com.codelabs.pdam.model.DetailReadMeterResponse

class PhotoLocationNFAdapter(val data: ArrayList<DetailReadMeterResponse.Data?> = arrayListOf()) :
    RecyclerView.Adapter<PhotoLocationNFAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFotoLocationNotFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PhotoLocationNFAdapter.ViewHolder {
        val binding =
            ItemFotoLocationNotFinishedBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoLocationNFAdapter.ViewHolder, position: Int) {
        val result = data[position]
        val image = holder.binding.fotoOrder

        Glide.with(holder.itemView.context)
            .load(result?.imagePathL)
            .into(image)

        holder.binding.deleteFoto.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setFotoLoc(datas: List<DetailReadMeterResponse.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}