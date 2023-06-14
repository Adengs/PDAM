package com.codelabs.pdam.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.databinding.ItemHistoryBinding
import com.codelabs.pdam.databinding.ItemHistoryPaymentBinding
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.model.HistoryDetailResponse
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryDetailAdapter (val data : ArrayList<HistoryDetailResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder>() {
    var onClick : ((HistoryDetailResponse.Data.Data?) -> Unit?)? = null

    inner class ViewHolder(val binding: ItemHistoryPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = SimpleDateFormat("dd-MMM-yyyy")
//        val date = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(dateFormat.parse(result?.payment?.updatedAt!!)!!)

//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val formatter = SimpleDateFormat("dd.MMM.yyyyy")
//        val output = formatter.format(parser.parse(result?.payment?.updatedAt))

        if (result?.payment == null){
            holder.binding.datetime.text = "Bikin data baru cok !!"
            holder.binding.belum.visibility = View.VISIBLE
        }else{
            holder.binding.datetime.text = date.format(dateFormat.parse(result.payment?.updatedAt!!)!!)

            if (result?.payment?.status == 0){
                holder.binding.belum.visibility = View.VISIBLE
                holder.binding.sudah.visibility = View.GONE
            } else {
                holder.binding.belum.visibility = View.GONE
                holder.binding.sudah.visibility = View.VISIBLE
            }
        }

        holder.binding.lastNoMeter.text = result?.lastMeterNumber.toString()
        holder.binding.nowMeter.text = result?.currentMeterNumber.toString()
        holder.binding.usageTime.text = result?.standMeterUsage.toString()
        holder.binding.total.text = rupiah.format(result?.billingAmount).toString().replace(",00", "")
            .replace("Rp", "").replace(",", ".")

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataPayment(datas: List<HistoryDetailResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}