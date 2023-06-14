package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemGolonganBinding
import com.codelabs.pdam.databinding.ItemProvinsiBinding
import com.codelabs.pdam.model.GolonganResponse
import com.codelabs.pdam.model.ProvinceResponse

class GolonganAdapter (val data : ArrayList<GolonganResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<GolonganAdapter.ViewHolder>() {
    var onClick: ((GolonganResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemGolonganBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGolonganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.golongan.text = result?.name.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setGolongan(datas: List<GolonganResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}