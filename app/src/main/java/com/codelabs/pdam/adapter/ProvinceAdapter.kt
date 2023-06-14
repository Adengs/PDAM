package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemNameCustBinding
import com.codelabs.pdam.databinding.ItemProvinsiBinding
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.model.ProvinceResponse

class ProvinceAdapter (val data : ArrayList<ProvinceResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {
    var onClick: ((ProvinceResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemProvinsiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProvinsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.provinsi.text = result?.name.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setProvince(datas: List<ProvinceResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}