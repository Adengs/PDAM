package com.codelabs.pdam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemCustomerBinding
import com.codelabs.pdam.databinding.ItemNameCustBinding
import com.codelabs.pdam.model.CustomerResponse

class CustomerContactAdapter (val data : ArrayList<CustomerResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<CustomerContactAdapter.ViewHolder>() {
    var onClick : ((CustomerResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemNameCustBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNameCustBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.nameCust.text = result?.name.toString()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataCust(datas: List<CustomerResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}