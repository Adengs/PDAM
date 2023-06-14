package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivitySetPasswordBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.NewInstallationResponse
import com.codelabs.pdam.model.UpdateResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetPassword : AppCompatActivity() {
    private lateinit var binding : ActivitySetPasswordBinding
    lateinit var sph : SharedPreference
    private var showPassword = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.newPassword.transformationMethod = ReallyHideMyPassword()
        binding.conPassword.transformationMethod = ReallyHideMyPassword()

        setEvent()
    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
//            startActivity(Intent(this, Login::class.java))
//            finishAndRemoveTask()
        }
        binding.hiderNewPassword.setOnClickListener {
            if (!showPassword){
                binding.hiderNewPassword.setImageResource(R.drawable.ic_eye_view)
                binding.newPassword.transformationMethod = null
                binding.newPassword.text?.length?.let { it1 -> binding.newPassword.setSelection(it1) }
                showPassword = true
            }else{
                binding.hiderNewPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.newPassword.transformationMethod = ReallyHideMyPassword()
                binding.newPassword.text?.length?.let { it1 -> binding.newPassword.setSelection(it1) }
                showPassword = false
            }
        }
        binding.hiderConPassword.setOnClickListener {
            if (!showPassword){
                binding.hiderConPassword.setImageResource(R.drawable.ic_eye_view)
                binding.conPassword.transformationMethod = null
                binding.conPassword.text?.length?.let { it1 -> binding.conPassword.setSelection(it1) }
                showPassword = true
            }else{
                binding.hiderConPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.conPassword.transformationMethod = ReallyHideMyPassword()
                binding.conPassword.text?.length?.let { it1 -> binding.conPassword.setSelection(it1) }
                showPassword = false
            }
        }
        binding.btnSave.setOnClickListener {
            setPassword()
        }
    }

    class ReallyHideMyPassword : PasswordTransformationMethod() {

        companion object {
            const val HIDE_CHAR = '*'
        }

        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return PasswordCharSequence(source)
        }

        inner class PasswordCharSequence (private val source: CharSequence) : CharSequence {

            override val length: Int
                get() = source.length

            override fun get(index: Int): Char = HIDE_CHAR

            override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                return source.subSequence(startIndex, endIndex)
            }
        }
    }

    private fun validate() : Boolean{
        var allValid = true
        val newpass = binding.newPassword.text.toString().trim()
        val conpass = binding.conPassword.text.toString().trim()

        //validasi kolom kosong
        if (newpass.isEmpty()){
            binding.newPassword.error = "Kolom tidak boleh kosong"
            binding.newPassword.requestFocus()
            allValid = false
        }
        if (conpass.isEmpty()){
            binding.conPassword.error = "Kolom tidak boleh kosong"
            binding.conPassword.requestFocus()
            allValid = false
        }

        //validasi password

        if (newpass.isNotEmpty() && conpass.isNotEmpty() && newpass != conpass){
            Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_LONG).show()
            allValid = false
        }


        //validasi password karakter
        if (newpass.isNotEmpty()  && newpass.length < 6 ){
            Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_LONG).show()
            allValid = false
        }
        if (conpass.isNotEmpty() && conpass.length < 6 ){
            Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_LONG).show()
            allValid = false
        }

        return allValid
    }

    private fun setPassword(){
        sph = SharedPreference(this)
        if (!validate()) {
            return
        }

        val newpass = binding.newPassword.text.toString().trim()
        val conpass = binding.conPassword.text.toString().trim()
        val email = sph.fetchEmail()

        val data = mapOf(
            "new_password" to newpass.toRequestBody(MultipartBody.FORM),
            "confirm_password" to conpass.toRequestBody(MultipartBody.FORM),
            "email" to email!!.toRequestBody(MultipartBody.FORM)
        )

        ApiConfig.instanceRetrofit(this).updatePassword(data).enqueue(object :
            Callback<UpdateResponse> {
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
//                        Toast.makeText(this@ChangePassword, responseBody.message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@SetPassword, VerificationSuccess::class.java))
                        finish()
                    }else {
                        response.errorBody()?.let { error ->
                            val parsing =
                                Gson().fromJson(error.string(), UpdateResponse::class.java)
                            Toast.makeText(this@SetPassword, parsing.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }else {
                    response.errorBody()?.let { error ->
                        val parsing =
                            Gson().fromJson(error.string(), UpdateResponse::class.java)
                        Toast.makeText(this@SetPassword, parsing.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@SetPassword, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        startActivity(Intent(this, Login::class.java))
//        finishAndRemoveTask()
    }
}