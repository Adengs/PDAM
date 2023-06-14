package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemGolonganBinding
import com.codelabs.pdam.databinding.ItemUkuranBinding
import com.codelabs.pdam.model.GolonganResponse
import com.codelabs.pdam.model.UkuranResponse

class UkuranAdapter (val data : ArrayList<UkuranResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<UkuranAdapter.ViewHolder>() {
    var onClick: ((UkuranResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemUkuranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUkuranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.ukuran.text = result?.size.toString()
        holder.binding.unit.text = result?.unit.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setUkuran(datas: List<UkuranResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}