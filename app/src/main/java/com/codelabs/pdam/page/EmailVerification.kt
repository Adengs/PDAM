package com.codelabs.pdam.page

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityEmailVerificationBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.EmailRequest
import com.codelabs.pdam.model.EmailVerificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailVerification : AppCompatActivity() {
    private lateinit var binding : ActivityEmailVerificationBinding
    lateinit var sph : SharedPreference
    private lateinit var startTime : CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        codeVerifText()

    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnSend.setOnClickListener {
            verifCode()
        }
        binding.resendCode.setOnClickListener {
            resendCode()
        }
    }

    private fun codeVerifText(){
//        val code1 = binding.code1.text.toString().trim()
//        val code2 = binding.code2.text.toString().trim()
//        val code3 = binding.code3.text.toString().trim()
//        val code4 = binding.code4.text.toString().trim()

        binding.code1.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s?.length == 1){
                    binding.code2.requestFocus()
                    binding.wrongMessage.visibility = View.GONE
                }
//                if (p3 < p2){
//                    binding.code1.requestFocus()
//                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.code2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s?.length == 1){
                    binding.code3.requestFocus()
                }
                if (p3 < p2){
                    binding.code1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.code3.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (s?.length == 1){
                    binding.code4.requestFocus()
                }
                if (p3 < p2){
                    binding.code2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.code4.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p3 < p2){
                    binding.code3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun verifCode() {
        sph = SharedPreference(this)
        val code1 = binding.code1.text.toString().trim()
        val code2 = binding.code2.text.toString().trim()
        val code3 = binding.code3.text.toString().trim()
        val code4 = binding.code4.text.toString().trim()
        val code = "${code1}${code2}${code3}${code4}"
        val verifcode = sph.fetchCode().toString().trim()

        if (code != verifcode) {
            binding.wrongMessage.visibility = View.VISIBLE
            return
        }

        startActivity(Intent(this, SetPassword::class.java))
        finish()
    }

    private fun resendCode(){
        sph = SharedPreference(this)
        val email = sph.fetchEmail()

        ApiConfig.instanceRetrofit(this).email(EmailRequest(email = email)).enqueue(object :
            Callback<EmailVerificationResponse> {

            override fun onResponse(
                call: Call<EmailVerificationResponse>,
                response: Response<EmailVerificationResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        sph.saveCode(responseBody.data?.code.toString())
                        binding.resendCode.visibility = View.GONE
                        binding.timer.visibility = View.VISIBLE
                        startTimer()
//                        startActivity(Intent(this@EmailVerification, EmailVerification::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<EmailVerificationResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    private fun startTimer(){
        object : CountDownTimer(180000,1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(remaining: Long) {
                val seconds = remaining/1000
                binding.timer.text = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60)
                Log.e("cek timer",(remaining/1000).toString())
            }

            override fun onFinish() {
                binding.timer.visibility = View.GONE
                binding.resendCode.visibility = View.VISIBLE
            }

        }.start()
    }
}