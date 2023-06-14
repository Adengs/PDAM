package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.HistoryAdapter
import com.codelabs.pdam.adapter.HistoryDetailAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityHistoryDetailBinding
import com.codelabs.pdam.eventbus.CustomerSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.model.HistoryDetailResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDetail : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryDetailBinding
    lateinit var sph : SharedPreference
    private lateinit var historyAdapter : HistoryDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        getHistoryDetail()
        setAdapterPayment()

        binding.nameCust.text = intent.getStringExtra(CUSTOMER_NAME)
        binding.id.text = intent.getStringExtra(CUSTOMER_ID)
        binding.location.text = HtmlCompat.fromHtml(intent.getStringExtra(CUSTOMER_ADDRESS)
            ?.replace("<p>","")
            ?.replace("<br>","") ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setAdapterPayment() {
        historyAdapter = HistoryDetailAdapter()
        binding.rvHistoryPayment.setHasFixedSize(true)
        binding.rvHistoryPayment.adapter = historyAdapter
        binding.rvHistoryPayment.layoutManager = LinearLayoutManager(this)
//
//        historyAdapter.onClick = {
//            EventBus.getDefault().post(CustomerSelected(it))
//            startActivity(Intent(this, HistoryDetail::class.java))
//            it?.customerId?.let { it1 -> sph.saveId(it1.toString()) }
//        }
    }

    private fun showDataPayment(data: List<HistoryDetailResponse.Data.Data?>) {
        historyAdapter.setDataPayment(data)
    }

    private fun getHistoryDetail(){
        sph = SharedPreference(this)
        val id = sph.fetchId()

        ApiConfig.instanceRetrofit(this).getHistoryDetail(id).enqueue(object : Callback<HistoryDetailResponse>{
            override fun onResponse(
                call: Call<HistoryDetailResponse>,
                response: Response<HistoryDetailResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (response.code() == 200){
//                        val items : List<HistoryDetailResponse.Data.Data?> =

                        responseBody.data?.data?.let { showDataPayment(it) }
                    }
                }
            }

            override fun onFailure(call: Call<HistoryDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    companion object {
        const val CUSTOMER_NAME = "CUSTOMER_NAME"
        const val CUSTOMER_ID = "CUSTOMER_ID"
        const val CUSTOMER_ADDRESS = "CUSTOMER_ADDRESS"
    }
}