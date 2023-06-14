package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemKabupatenBinding
import com.codelabs.pdam.databinding.ItemKecamatanBinding
import com.codelabs.pdam.model.KabupatenResponse
import com.codelabs.pdam.model.KecamatanResponse

class KecamatanAdapter (val data : ArrayList<KecamatanResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<KecamatanAdapter.ViewHolder>() {
    var onClick: ((KecamatanResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemKecamatanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKecamatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.kecamatan.text = result?.name.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setKecamatan(datas: List<KecamatanResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}