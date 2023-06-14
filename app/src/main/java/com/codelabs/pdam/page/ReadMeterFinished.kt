package com.codelabs.pdam.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.PhotoLocationFAdapter
import com.codelabs.pdam.adapter.PhotoLocationNFAdapter
import com.codelabs.pdam.adapter.PhotoMeterFAdapter
import com.codelabs.pdam.adapter.PhotoMeterNFAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityReadMeterFinishedBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.DetailReadMeterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class ReadMeterFinished : AppCompatActivity() {
    lateinit var sph: SharedPreference
    private lateinit var binding : ActivityReadMeterFinishedBinding
    private lateinit var fotoAdapterLocationF : PhotoLocationFAdapter
    private lateinit var fotoAdapterMeterF : PhotoMeterFAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMeterFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterFotoLocation()
        setAdapterFotoMeter()
        getData()
    }

    private fun setEvent(){
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setAdapterFotoLocation() {
        fotoAdapterLocationF = PhotoLocationFAdapter()
        binding.rvUploadImage.setHasFixedSize(true)
        binding.rvUploadImage.adapter = fotoAdapterLocationF
        binding.rvUploadImage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setAdapterFotoMeter() {
        fotoAdapterMeterF = PhotoMeterFAdapter()
        binding.rvUploadImageMeter.setHasFixedSize(true)
        binding.rvUploadImageMeter.adapter = fotoAdapterMeterF
        binding.rvUploadImageMeter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setFotoLocation(data: List<String?>) {
        fotoAdapterLocationF.setFotoLoc(data)
    }

    private fun setFotoMeter(data: List<String?>) {
        fotoAdapterMeterF.setFotoMet(data)
    }

    private fun getData() {
        sph = SharedPreference(this)
        val id = sph.fetchId()
        val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        ApiConfig.instanceRetrofit(this).getDetailRM(id)
            .enqueue(object : Callback<DetailReadMeterResponse> {
                override fun onResponse(
                    call: Call<DetailReadMeterResponse>,
                    response: Response<DetailReadMeterResponse>,
                ) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        if (responseBody.statusCode == 200) {
                            binding.nameCust.text = responseBody.data?.customer?.name.toString()
                            binding.id.text = responseBody.data?.customerId.toString()
                            binding.location.text =
                                Html.fromHtml(responseBody.data?.customer?.address?.replace("<p>",
                                    "")?.replace("<br>", ""))
                            binding.etLastMeter.setText(responseBody.data?.lastMeterNumber.toString())

                            responseBody.data?.photoLocation?.let { setFotoLocation(it) }
                            responseBody.data?.photoStandMeter?.let { setFotoMeter(it) }
                            binding.etPenggunaanMeter.setText(responseBody.data?.standMeterUsage.toString())
                            binding.etNowMeter.setText(responseBody.data?.currentMeterNumber.toString())
                            binding.etJumlah.setText(rupiah.format(responseBody.data?.billingAmount).toString().replace(",00", "")
                                .replace("Rp", "").replace(",", "."))
                            binding.desc.setText(responseBody.data?.notes.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<DetailReadMeterResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}