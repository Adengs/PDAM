package com.codelabs.pdam.page

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityCustomerInformationDetailBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.CustomerDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerInformationDetail : AppCompatActivity() {
    private lateinit var binding : ActivityCustomerInformationDetailBinding
    lateinit var sph: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerInformationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        getData()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData(){
        sph = SharedPreference(this)
        val id = sph.fetchId()

        ApiConfig.instanceRetrofit(this).getDetailCust(id).enqueue(object : Callback<CustomerDetailResponse>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CustomerDetailResponse>,
                response: Response<CustomerDetailResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        binding.nameCust.text = responseBody.data?.name.toString()
                        binding.id.text = responseBody.data?.customerId.toString()
                        binding.phone.text = responseBody.data?.phone.toString()
                        binding.location.text = Html.fromHtml(responseBody.data?.address?.replace("<p>", "")?.replace("<br>", ""))
//                        binding.etNameCustBank.setText(responseBody.data?.bankAccountName.toString())
//                        binding.etNoRekening.setText(responseBody.data?.bankAccountNumber.toString())
                        binding.etProvince.setText(responseBody.data?.installation?.province?.name.toString())
                        binding.etKabupaten.setText(responseBody.data?.installation?.city?.name.toString())
                        binding.etKecamatan.setText(responseBody.data?.installation?.district?.name.toString())
                        binding.etPosCode.setText(responseBody.data?.installation?.postalCode.toString())
                        binding.etAddresses.setText(Html.fromHtml(responseBody.data?.address?.replace("<p>", "")?.replace("<br>", "")))
//                        binding.etNik.setText(responseBody.data?.nik.toString())
//                        binding.etLuas.setText("${responseBody.data?.installation?.land?.landArea.toString()} (m2)")
//                        binding.etPanjang.setText("${responseBody.data?.installation?.land?.landLength.toString()} (m)")
//                        binding.etLebar.setText("${responseBody.data?.installation?.land?.landBreadth.toString()} (m)")
//                        binding.etLuasBangunan.setText("${responseBody.data?.installation?.land?.buildingArea.toString()} (m2)")
//                        binding.etPanjangBangunan.setText("${responseBody.data?.installation?.land?.buildingLength.toString()} (m)")
//                        binding.etLebarBangunan.setText("${responseBody.data?.installation?.land?.buildingBreadth.toString()} (m)")
                    }
                }
            }

            override fun onFailure(call: Call<CustomerDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}