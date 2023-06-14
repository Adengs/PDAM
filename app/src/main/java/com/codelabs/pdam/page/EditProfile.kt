package com.codelabs.pdam.page

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityEditProfileBinding
import com.codelabs.pdam.databinding.ActivitySetPasswordBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.ProfileResponse
import com.codelabs.pdam.model.UpdateProfileResponse
import com.codelabs.pdam.utils.InputStreamRequestBody
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class EditProfile : AppCompatActivity() {
    lateinit var sph : SharedPreference
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    override fun onResume() {
        super.onResume()
        setProfile()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.phone.setOnClickListener {
            binding.phone.text?.length?.let { it1 -> binding.phone.setSelection(it1) }
        }
        binding.layImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.btnSave.setOnClickListener {
            updateProfile()
        }
    }

    private fun setProfile() {
        sph = SharedPreference(this)
        val image = binding.imageProfil

        ApiConfig.instanceRetrofit(this).getProfile().enqueue(object :
            Callback<ProfileResponse> {

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>,
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.statusCode == 200) {
//                        if (activity != null){
                        Glide.with(this@EditProfile)
                            .load(responseBody.data?.profile?.photo.toString())
                            .into(image)
//                        }

                        binding.name.setText(responseBody.data?.profile?.name.toString())
                        binding.email.setText(responseBody.data?.email.toString())
                        binding.phone.setText(responseBody.data?.profile?.phone.toString())
                        binding.job.setText(responseBody.data?.role?.name.toString())

                        sph.saveName(responseBody.data?.profile?.name.toString())
                        sph.saveEmail(responseBody.data?.email.toString())
                        sph.savePhone(responseBody.data?.profile?.phone.toString())
                        sph.savePhoto(responseBody.data?.profile?.photo.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                Log.e("cek file uri", fileUri.toString())
                uploadFoto(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uploadFoto(fileUri: Uri) {
        sph = SharedPreference(this)
//        val name = sph.fetchName().toString()
//        val email = sph.fetchEmail().toString()
//        val username = sph.fetchUsername().toString()
//        val phone = sph.fetchPhone().toString()

        val requestFile: RequestBody = InputStreamRequestBody(this, fileUri)
        Log.e("cekfoto", fileUri.toString())
        var imageBody: MultipartBody.Part? = null
        imageBody = MultipartBody.Part.createFormData("photo", fileUri.toString(),requestFile)
//        val data = mapOf(
//            "name" to name.toRequestBody(MultipartBody.FORM),
//            "email" to email.toRequestBody(MultipartBody.FORM),
//            "username" to username.toRequestBody(MultipartBody.FORM),
//            "phone" to phone.toRequestBody(MultipartBody.FORM)
//        )

        ApiConfig.instanceRetrofit(this)
            .updatePhoto(imageBody)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody?.statusCode == 200) {
                        Toast.makeText(this@EditProfile, responseBody.message, Toast.LENGTH_LONG).show()
                        Log.e("Auth", responseBody.toString())
                        setProfile()
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@EditProfile, t.message, Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun updateProfile(){
        sph = SharedPreference(this)
//        val name = sph.fetchName().toString()
//        val email = sph.fetchEmail().toString()
//        val username = sph.fetchUsername().toString()
        val phone = binding.phone.text.toString().trim()

        if (phone.isEmpty()) {
            binding.phone.error = "Username tidak boleh kosong"
            binding.phone.requestFocus()
            return
        }else if (phone.isNotEmpty() && phone.length < 9){
            Toast.makeText(this, "Nomor telepon minimal 9 karakter", Toast.LENGTH_LONG).show()
            return
        }else if (phone.isNotEmpty() && phone.length > 16){
            Toast.makeText(this, "Nomor telepon maksimal 16 karakter", Toast.LENGTH_LONG).show()
            return
        }
//        val photo = Uri.fromFile(getFileStreamPath(sph.fetchPhoto().toString()))
//        val photo = Uri.parse(sph.fetchPhoto().toString())
//        Log.e("cek uri", photo.toString())


//        val requestFile: RequestBody = InputStreamRequestBody(this, photo)
//        var imageBody: MultipartBody.Part? = null
//        imageBody = MultipartBody.Part.createFormData("photo", photo.toString(),requestFile)
        val data = mapOf(
//            "name" to name.toRequestBody(MultipartBody.FORM),
//            "email" to email.toRequestBody(MultipartBody.FORM),
//            "username" to username.toRequestBody(MultipartBody.FORM),
            "phone" to phone.toRequestBody(MultipartBody.FORM)
        )

        ApiConfig.instanceRetrofit(this)
            .updateProfile(data)
            .enqueue(object : Callback<UpdateProfileResponse> {
                override fun onResponse(
                    call: Call<UpdateProfileResponse>,
                    response: Response<UpdateProfileResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody?.statusCode == 200) {
                        Toast.makeText(this@EditProfile, responseBody.message, Toast.LENGTH_LONG).show()
                        Log.e("Auth", responseBody.toString())
                        finish()
                        onBackPressed()
//                        setProfile()
                    }
                }

                override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@EditProfile, t.message, Toast.LENGTH_LONG).show()
                    t.message?.let { Log.e("Auth2", it) }
                }

            })
    }
}