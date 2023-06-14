package com.codelabs.pdam.page

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityEcomplainBinding
import com.codelabs.pdam.eventbus.CustomerSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.ComplainResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EComplain : AppCompatActivity() {
    private lateinit var binding : ActivityEcomplainBinding
    lateinit var sph : SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEcomplainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
        binding.nameCust.setOnClickListener {
            val requestCode = 1
            val intent = Intent(this, NameCustomer::class.java)
            startActivityForResult(intent, requestCode)
        }
        binding.icNameCust.setOnClickListener {
            val requestCode = 1
            val intent = Intent(this, NameCustomer::class.java)
            startActivityForResult(intent, requestCode)
        }
        binding.btnSend.setOnClickListener {
            sendComplain()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        sph = SharedPreference(this)

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                binding.nameCust.setText(data!!.getStringExtra("namecust"))
                Log.e("cek", data.getStringExtra("namecust").toString())
            }else{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
//        if (resultCode != Activity.RESULT_OK) return
//        when(requestCode){
//            [1] -> {binding.nameCust.setText(data?.getStringExtra("namecust"))}
//        }
//        if (requestCode == 1){
//            binding.nameCust.setText(sph.fetchNameCust())
//        }
    }

    private fun sendComplain(){
        val id = sph.fetchId()
        val subject = binding.etSubject.text.toString().trim()
        val desc = binding.etDesc.text.toString().trim()

        if (subject.isEmpty()){
            binding.etSubject.error = "Kolom tidak boleh kosong"
            binding.etSubject.requestFocus()
            return
        }
        if (desc.isEmpty()){
            binding.etDesc.error = "Kolom tidak boleh kosong"
            binding.etDesc.requestFocus()
            return
        }
        if (desc.isNotEmpty() && desc.length < 2){
            binding.etSubject.error = "Subject minimal 2 karakter"
            binding.etSubject.requestFocus()
            return
        }

        val data = mapOf(
            "customer_id" to id!!.toRequestBody(MultipartBody.FORM),
            "subject" to subject.toRequestBody(MultipartBody.FORM),
            "description" to desc.toRequestBody(MultipartBody.FORM)
        )

        ApiConfig.instanceRetrofit(this).complain(data).enqueue(object : Callback<ComplainResponse>{

            override fun onResponse(
                call: Call<ComplainResponse>,
                response: Response<ComplainResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        Toast.makeText(this@EComplain, "Complain berhasil dikirim", Toast.LENGTH_LONG).show()
                        onBackPressed()
                    }
                }
            }

            override fun onFailure(call: Call<ComplainResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}