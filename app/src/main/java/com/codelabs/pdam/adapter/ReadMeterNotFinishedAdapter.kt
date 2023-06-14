package com.codelabs.pdam.adapter

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ItemNotFinishedBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.DetailReadMeterResponse
import com.codelabs.pdam.model.ReadMeterResponse
import com.codelabs.pdam.page.ReadMeterNotFinished
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadMeterNotFinishedAdapter(val data: ArrayList<ReadMeterResponse.Data.Data?> = arrayListOf()) :
    RecyclerView.Adapter<ReadMeterNotFinishedAdapter.ViewHolder>() {
    var onClick: ((ReadMeterResponse.Data.Data?) -> Unit?)? = null
    lateinit var sph : SharedPreference
    var value = ""

    inner class ViewHolder(val binding: ItemNotFinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReadMeterNotFinishedAdapter.ViewHolder {
        val binding = ItemNotFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ReadMeterNotFinishedAdapter.ViewHolder, position: Int) {
        val result = data[position]

        holder.binding.nameCust.text = result?.customer?.name.toString()
        holder.binding.id.text = result?.customer?.customerId.toString()
        holder.binding.location.text = Html.fromHtml(result?.customer?.address?.replace("<p>",""))

//        holder.binding.layDataNf.setOnClickListener {
////            value = result?.readMeterId.toString()
//            movePage(holder, position)
//        }
    }

    private fun movePage(holder: ViewHolder, position: Int) {
        sph = SharedPreference(holder.itemView.context)
        val id = value

        ApiConfig.instanceRetrofit(holder.itemView.context).getDetailRM(id).enqueue(object : Callback<DetailReadMeterResponse>{
            override fun onResponse(
                call: Call<DetailReadMeterResponse>,
                response: Response<DetailReadMeterResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        Log.e("Auth", responseBody.toString())
                        sph.saveId(value)
                        data.removeAt(position)
                        notifyItemRemoved(position)
                        holder.itemView.context.startActivity(Intent(holder.itemView.context, ReadMeterNotFinished::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<DetailReadMeterResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataNotFinished(datas: List<ReadMeterResponse.Data.Data?>) {
        data.clear()
        data.addAll(datas)
        notifyDataSetChanged()
    }
}