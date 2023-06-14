package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityLoginBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.LoginResponse
import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    lateinit var sph: SharedPreference
    private lateinit var binding: ActivityLoginBinding
    private var showPassword = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.password.transformationMethod = ReallyHideMyPassword()
        checkRememberMe()
        setEvent()
    }

    private fun setEvent() {
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        binding.btnLogin.setOnClickListener {
            login()
//            startActivity(Intent(this, MainActivity::class.java))
//            Toast.makeText(this, "On Develop :(", Toast.LENGTH_LONG).show()
        }
        binding.hiderPassword.setOnClickListener {
            if (!showPassword) {
                binding.hiderPassword.setImageResource(R.drawable.ic_eye_view)
                binding.password.transformationMethod = null
                binding.password.text?.length?.let { it1 -> binding.password.setSelection(it1) }
                showPassword = true
            } else {
                binding.hiderPassword.setImageResource(R.drawable.ic_eye_gone)
                binding.password.transformationMethod = ReallyHideMyPassword()
                binding.password.text?.length?.let { it1 -> binding.password.setSelection(it1) }
                showPassword = false
            }
        }
        binding.cbRememberMe.setOnCheckedChangeListener{buttonView, isChecked ->
            sph = SharedPreference(this)
            sph.saveRemember(isChecked)
//            Log.e("cek", isChecked.toString())
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

    private fun checkRememberMe(){
        sph = SharedPreference(this)
        if (sph.fetchRemember() == true){
            binding.cbRememberMe.isChecked = true
            binding.username.setText(sph.fetchUsername().toString())
            binding.password.setText(sph.fetchPassword().toString())
        }
    }

    private fun login() {
        sph = SharedPreference(this)
        val username = binding.username.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val data = mutableMapOf<String, RequestBody>()

        if (username.isEmpty()) {
            binding.username.error = "Username tidak boleh kosong"
            binding.username.requestFocus()
            return
        } else if (password.isEmpty()) {
            binding.password.error = "Kata sandi tidak boleh kosong"
            binding.password.requestFocus()
            return
        }

        data["email"] = username.toRequestBody()
        data["password"] = password.toRequestBody()

        ApiConfig.instanceRetrofit(this).login(data).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                var logresponse = response.body()

                if (logresponse != null) {
                    if (logresponse.statusCode == 200) {
                        Toast.makeText(this@Login, logresponse.message, Toast.LENGTH_LONG).show()
                        sph.saveAuthToken(logresponse.data?.token.toString())
                        sph.saveUsername(binding.username.text.toString())
                        sph.savePassword(binding.password.text.toString())
                        sph.saveIdProv(logresponse.data?.user?.company?.province?.provinceId.toString())
                        sph.saveIdKab(logresponse.data?.user?.company?.city?.code.toString())
                        sph.saveIdKec(logresponse.data?.user?.district?.code.toString())
                        sph.saveProvince(logresponse.data?.user?.company?.province?.name.toString())
                        sph.saveKabupaten(logresponse.data?.user?.company?.city?.name.toString())
                        sph.saveKecamtan(logresponse.data?.user?.district?.name.toString())
                        startActivity(Intent(this@Login, MainActivity::class.java))
                        finish()
                        Log.e("Auth", logresponse.toString())
                        sph.put(login = true)
                    }
                    if (response.code() == 402) {
                        Toast.makeText(this@Login, "Sesi berakhir, silahkan melakukan login ulang", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@Login, Login::class.java))
                        sph.put(false)
                        finish()
                    }
                    else {
                        response.errorBody()?.let { error ->
                            val parsing =
                                Gson().fromJson(error.string(), LoginResponse::class.java)
//                            Toast.makeText(this@Login, parsing.message, Toast.LENGTH_LONG)
//                                .show()
                            Toast.makeText(this@Login, "Username/Password salah", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                } else {
                    response.errorBody()?.let { error ->
                        val parsing =
                            Gson().fromJson(error.string(), LoginResponse::class.java)
                        Toast.makeText(this@Login, "Username/Password salah", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}