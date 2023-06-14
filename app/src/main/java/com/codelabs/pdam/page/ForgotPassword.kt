package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityForgotPasswordBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.EmailRequest
import com.codelabs.pdam.model.EmailVerificationResponse
import com.codelabs.pdam.model.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    lateinit var sph : SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnSend.setOnClickListener {
            getCode()
            binding.btnSend.isClickable = false
//            startActivity(Intent(this@ForgotPassword, EmailVerification::class.java))
        }
    }
    private fun getCode(){
        sph = SharedPreference(this)
        val email = binding.email.text.toString().trim()

        if (email.isEmpty()){
            binding.email.error = "Email tidak boleh kosong"
            binding.email.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit(this).email(EmailRequest(email = email)).enqueue(object : Callback<EmailVerificationResponse>{

            override fun onResponse(
                call: Call<EmailVerificationResponse>,
                response: Response<EmailVerificationResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        sph.saveEmail(email)
                        sph.saveCode(responseBody.data?.code.toString())
                        startActivity(Intent(this@ForgotPassword, EmailVerification::class.java))
                        finish()
                    }
                    else {
                        response.errorBody()?.let { error ->
                            val parsing =
                                Gson().fromJson(error.string(), LoginResponse::class.java)
//                            Toast.makeText(this@Login, parsing.message, Toast.LENGTH_LONG)
//                                .show()
                            Toast.makeText(this@ForgotPassword, "Email tidak terdaftar", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                else {
                    response.errorBody()?.let { error ->
                        val parsing =
                            Gson().fromJson(error.string(), LoginResponse::class.java)
//                            Toast.makeText(this@Login, parsing.message, Toast.LENGTH_LONG)
//                                .show()
                        Toast.makeText(this@ForgotPassword, "Email tidak terdaftar", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<EmailVerificationResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}