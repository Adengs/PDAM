package com.codelabs.pdam.adapter

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ItemFinishedBinding
import com.codelabs.pdam.databinding.ItemNotFinishedBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.DetailReadMeterResponse
import com.codelabs.pdam.model.ReadMeterResponse
import com.codelabs.pdam.page.ReadMeterFinished
import com.codelabs.pdam.page.ReadMeterNotFinished
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadMeterFinishedAdapter (val data: ArrayList<ReadMeterResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<ReadMeterFinishedAdapter.ViewHolder>()  {
    var onClick: ((ReadMeterResponse.Data.Data?) -> Unit?)? = null
    lateinit var sph : SharedPreference
    var value = ""

    inner class ViewHolder(val binding: ItemFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.nameCust.text = result?.customer?.name.toString()
        holder.binding.id.text = result?.customer?.customerId.toString()
        holder.binding.location.text = Html.fromHtml(result?.customer?.address?.replace("<p>","")?.replace("<br>",""))

//        holder.binding.layDataF.setOnClickListener {
//            value = result?.readMeterId.toString()
//            movePage(holder, position)
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataFinished(datas: List<ReadMeterResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }

    private fun movePage(holder: ReadMeterFinishedAdapter.ViewHolder, position: Int) {
        val id = value

        ApiConfig.instanceRetrofit(holder.itemView.context).getDetailRM(id).enqueue(object :
            Callback<DetailReadMeterResponse> {
            override fun onResponse(
                call: Call<DetailReadMeterResponse>,
                response: Response<DetailReadMeterResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        Log.e("Auth", responseBody.toString())
                        holder.itemView.context.startActivity(Intent(holder.itemView.context, ReadMeterFinished::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<DetailReadMeterResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}