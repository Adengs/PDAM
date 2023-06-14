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
import com.codelabs.pdam.databinding.ActivityChangePasswordBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.UpdateResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {

    lateinit var sph : SharedPreference
    private lateinit var binding: ActivityChangePasswordBinding
    private var showPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.oldPassword.transformationMethod = ReallyHideMyPassword()
        binding.newPassword.transformationMethod = ReallyHideMyPassword()
        binding.conPassword.transformationMethod = ReallyHideMyPassword()

        setEvent()
    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
        binding.hiderOldPassword.setOnClickListener {
            if (!showPassword) {
                binding.hiderOldPassword.setImageResource(R.drawable.ic_eye_view)
                binding.oldPassword.transformationMethod = null
                binding.oldPassword.text?.length?.let { it1 -> binding.oldPassword.setSelection(it1) }
                showPassword = true
            } else {
                binding.hiderOldPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.oldPassword.transformationMethod = ReallyHideMyPassword()
                binding.oldPassword.text?.length?.let { it1 -> binding.oldPassword.setSelection(it1) }
                showPassword = false
            }
        }
        binding.hiderNewPassword.setOnClickListener {
            if (!showPassword) {
                binding.hiderNewPassword.setImageResource(R.drawable.ic_eye_view)
                binding.newPassword.transformationMethod = null
                binding.newPassword.text?.length?.let { it1 -> binding.newPassword.setSelection(it1) }
                showPassword = true
            } else {
                binding.hiderNewPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.newPassword.transformationMethod = ReallyHideMyPassword()
                binding.newPassword.text?.length?.let { it1 -> binding.newPassword.setSelection(it1) }
                showPassword = false
            }
        }
        binding.hiderConPassword.setOnClickListener {
            if (!showPassword) {
                binding.hiderConPassword.setImageResource(R.drawable.ic_eye_view)
                binding.conPassword.transformationMethod = null
                binding.conPassword.text?.length?.let { it1 -> binding.conPassword.setSelection(it1) }
                showPassword = true
            } else {
                binding.hiderConPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.conPassword.transformationMethod = ReallyHideMyPassword()
                binding.conPassword.text?.length?.let { it1 -> binding.conPassword.setSelection(it1) }
                showPassword = false
            }
        }
        binding.btnSave.setOnClickListener {
            updatePassword()
        }
    }

    class ReallyHideMyPassword : PasswordTransformationMethod() {

        companion object {
            const val HIDE_CHAR = '*'
        }

        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return PasswordCharSequence(source)
        }

        inner class PasswordCharSequence(private val source: CharSequence) : CharSequence {

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
        sph = SharedPreference(this)
        val conpassold = sph.fetchPassword().toString().trim()
        val oldpass = binding.oldPassword.text.toString().trim()
        val newpass = binding.newPassword.text.toString().trim()
        val conpass = binding.conPassword.text.toString().trim()

        //validasi kolom kosong
        if (oldpass.isEmpty()){
            binding.oldPassword.error = "Kolom tidak boleh kosong"
            binding.oldPassword.requestFocus()
            allValid = false
        }
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
        if (oldpass.isNotEmpty() && oldpass != conpassold){
            binding.oldPassword.error = "Password lama salah"
            binding.oldPassword.requestFocus()
            allValid = false
        }
        if (newpass.isNotEmpty() && conpass.isNotEmpty() && newpass != conpass){
            Toast.makeText(this, "Konfirmasi password tidak cocok", Toast.LENGTH_LONG).show()
            allValid = false
        }
        if (oldpass.isNotEmpty() && newpass.isNotEmpty() && oldpass == newpass){
            Toast.makeText(this, "Password baru tidak boleh sama dengan password lama", Toast.LENGTH_LONG).show()
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
        if (oldpass.isNotEmpty() && oldpass.length < 6){
            Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_LONG).show()
            allValid = false
        }

        return allValid
    }

    private fun updatePassword() {
        sph = SharedPreference(this)
        if (!validate()) {
            return
        }

        val oldpass = binding.oldPassword.text.toString().trim()
        val newpass = binding.newPassword.text.toString().trim()
        val conpass = binding.conPassword.text.toString().trim()
        val email = sph.fetchEmail()!!

        val data = mapOf(
            "current_password" to oldpass.toRequestBody(MultipartBody.FORM),
            "new_password" to newpass.toRequestBody(MultipartBody.FORM),
            "confirm_password" to conpass.toRequestBody(MultipartBody.FORM),
            "email" to email.toRequestBody(MultipartBody.FORM)
        )

        ApiConfig.instanceRetrofit(this).updatePassword(data).enqueue(object : Callback<UpdateResponse>{
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200){
//                        Toast.makeText(this@ChangePassword, responseBody.message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@ChangePassword, ChangePasswordSuccess::class.java))
                        finish()
                    }else {
                        Toast.makeText(this@ChangePassword, responseBody?.message, Toast.LENGTH_LONG).show()
//                        response.errorBody()?.let { error ->
//                            val parsing =
//                                Gson().fromJson(error.string(), UpdateResponse::class.java)
//                            Toast.makeText(this@ChangePassword, parsing.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
                    }
                }else {
                    Toast.makeText(this@ChangePassword, responseBody?.message, Toast.LENGTH_LONG).show()
//                    response.errorBody()?.let { error ->
//                        val parsing =
//                            Gson().fromJson(error.string(), UpdateResponse::class.java)
//                        Toast.makeText(this@ChangePassword, parsing.message, Toast.LENGTH_LONG)
//                            .show()
//                    }
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ChangePassword, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}