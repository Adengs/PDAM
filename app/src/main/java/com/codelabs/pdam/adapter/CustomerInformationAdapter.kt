package com.codelabs.pdam.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemCustomerBinding
import com.codelabs.pdam.databinding.ItemNotFinishedBinding
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.model.ReadMeterResponse

class CustomerInformationAdapter (val data : ArrayList<CustomerResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<CustomerInformationAdapter.ViewHolder>(){
        var onClick : ((CustomerResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.nameCust.text = result?.name.toString()
        holder.binding.id.text = result?.customerId.toString()
        holder.binding.phone.text = result?.phone.toString()
        holder.binding.location.text = Html.fromHtml(result?.address?.replace("<p>","")
            ?.replace("<br>",""))
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