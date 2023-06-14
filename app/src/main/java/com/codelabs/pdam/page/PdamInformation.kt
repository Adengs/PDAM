package com.codelabs.pdam.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import androidx.core.text.HtmlCompat
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityPdamInformationBinding
import com.codelabs.pdam.model.About
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PdamInformation : AppCompatActivity() {
    private lateinit var binding : ActivityPdamInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdamInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        getInformation()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getInformation() {
        ApiConfig.instanceRetrofit(this).getAbout().enqueue(object : Callback<About>{

            override fun onResponse(call: Call<About>, response: Response<About>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        binding.textInfo.text = HtmlCompat.fromHtml(responseBody.data?.description ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
                    }
                }
            }

            override fun onFailure(call: Call<About>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}