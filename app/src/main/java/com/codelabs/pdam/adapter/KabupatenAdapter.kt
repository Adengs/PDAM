package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemKabupatenBinding
import com.codelabs.pdam.databinding.ItemProvinsiBinding
import com.codelabs.pdam.model.KabupatenResponse
import com.codelabs.pdam.model.ProvinceResponse

class KabupatenAdapter (val data : ArrayList<KabupatenResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<KabupatenAdapter.ViewHolder>() {
    var onClick: ((KabupatenResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemKabupatenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKabupatenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.kabupaten.text = result?.name.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setKabupaten(datas: List<KabupatenResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}