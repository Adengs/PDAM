package com.codelabs.pdam.page

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.adapter.PhotoLocationNFAdapter
import com.codelabs.pdam.adapter.PhotoMeterNFAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityReadMeterNotFinishedBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.NumberFormat
import java.util.*

class ReadMeterNotFinished : AppCompatActivity() {
    lateinit var sph: SharedPreference
    private lateinit var binding: ActivityReadMeterNotFinishedBinding
    private lateinit var fotoAdapterLocationNF: PhotoLocationNFAdapter
    private lateinit var fotoAdapterMeterNF: PhotoMeterNFAdapter
    var price = " "
    var notes = "Tidak ada catatan"
    var current = " "
    var priceMeter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadMeterNotFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        getData()
//        getPrice()
        setAdapterFotoLocation()
        setAdapterFotoMeter()
        priceInput()
    }

    private fun setEvent() {
        binding.layBack.setOnClickListener {
            onBackPressed()
        }
        binding.layAddFoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    startForProfileImageResultL.launch(intent)
                }

        }
        binding.layAddFotoMeter.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent ->
                    startForProfileImageResultM.launch(intent)
                }

        }
//        binding.etNowMeter.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                price = s.toString()
//                setPrice()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//        })
        binding.btnSend.setOnClickListener {
            binding.etPenggunaanMeter.clearFocus()
            binding.etNowMeter.clearFocus()
            updateData()
        }
    }

    private fun calculatePrice(){
        val customerId = binding.id.text.toString().trim()
        val lastMeter = binding.etLastMeter.text.toString().trim()
        val currentMeter = current
//        val price = priceMeter

        val data = mutableMapOf<String, RequestBody>()
        data["customer_id"] = customerId.toRequestBody()
        data["last_meter_number"] = lastMeter.toRequestBody()
        data["current_meter_number"] = currentMeter.toRequestBody()

        ApiConfig.instanceRetrofit(this).calculate(data).enqueue(object : Callback<CalculateResponse>{

            override fun onResponse(
                call: Call<CalculateResponse>,
                response: Response<CalculateResponse>,
            ) {
                val responseBody = response.body()
                val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

//                if (responseBody != null) {
                    if (responseBody?.statusCode == 200) {
                        binding.etJumlah.setText(rupiah.format(responseBody.data?.billingAmount).toString().replace(",00", "")
                            .replace("Rp", "").replace(",", "."))

//                        try {
//                            binding.etJumlah.setText(rupiah.format(responseBody.data?.billingAmount).toString().replace(",00", "")
//                                .replace("Rp", "").replace(",", "."))
//                        } catch (ex: NumberFormatException) {
//                            binding.etJumlah.setText("0")
//                        }

                    }else {
//                        Toast.makeText(this@ReadMeterNotFinished, responseBody?.message.toString(), Toast.LENGTH_LONG).show()
                        response.errorBody()?.let { error ->
                            val parsing =
                                Gson().fromJson(error.string(), CalculateResponse::class.java)
                            Toast.makeText(this@ReadMeterNotFinished,
                                parsing.message,
                                Toast.LENGTH_LONG)
                                .show()

                            binding.etJumlah.setText("0")
                        }
//                    }
                }
            }

            override fun onFailure(call: Call<CalculateResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ReadMeterNotFinished, t.message.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun priceInput(){
        binding.etNowMeter.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                current = char.toString()
                calculatePrice()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.etNowMeter.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_NEXT){
                    binding.etNowMeter.hideKeyboard()
                    return true
                }

                return false
            }

        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

//    private fun getPrice() {
//        sph = SharedPreference(this)
//
//        ApiConfig.instanceRetrofit(this).getAbout().enqueue(object : Callback<About> {
//
//            override fun onResponse(call: Call<About>, response: Response<About>) {
//                val responseBody = response.body()
//
//                if (responseBody != null) {
//                    if (responseBody.statusCode == 200) {
//                        sph.savePrice(responseBody.data?.pricePerMeter.toString())
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<About>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//        })
//    }

//    private fun setPrice() {
//        sph = SharedPreference(this)
//        val pricePerMeter = sph.fetchPrice()!!.toInt()
//        val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
//
//        try {
//            val tPrice = price.toInt().times(pricePerMeter)
//            binding.etJumlah.setText(rupiah.format(tPrice).toString().replace(",00", "")
//                .replace("Rp", "").replace(",", "."))
//        } catch (ex: NumberFormatException) {
//            binding.etJumlah.setText("0")
//        }
//
//    }

    private fun getData() {
        sph = SharedPreference(this)
        val id = sph.fetchId()

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
                                    ""))
                            binding.etPenggunaanMeter.setText(responseBody.data?.standMeterUsage.toString())
                            binding.etLastMeter.setText(responseBody.data?.lastMeterNumber.toString())

//                            responseBody.data?.let { setFotoLocation(listOf(it)) }
//                            responseBody.data?.let { setFotoMeter(listOf(it)) }
                        }
                    }
                }

                override fun onFailure(call: Call<DetailReadMeterResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun setAdapterFotoLocation() {
        fotoAdapterLocationNF = PhotoLocationNFAdapter()
        binding.rvUploadImage.setHasFixedSize(true)
        binding.rvUploadImage.adapter = fotoAdapterLocationNF
        binding.rvUploadImage.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setAdapterFotoMeter() {
        fotoAdapterMeterNF = PhotoMeterNFAdapter()
        binding.rvUploadImageMeter.setHasFixedSize(true)
        binding.rvUploadImageMeter.adapter = fotoAdapterMeterNF
        binding.rvUploadImageMeter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setFotoLocation(data: List<DetailReadMeterResponse.Data?>) {
        fotoAdapterLocationNF.setFotoLoc(data)
    }

    private fun setFotoMeter(data: List<DetailReadMeterResponse.Data?>) {
        fotoAdapterMeterNF.setFotoMet(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val startForProfileImageResultL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK

                    val fileUri = data?.data!!
                    val selectedImageFile =
                        File(Objects.requireNonNull(FileUriUtils.getRealPath(this, fileUri)))
                    //                binding.fotoOrder.setImageURI(fileUri)
                    //                uploadFoto(fileUri)
                    fotoAdapterLocationNF.data.add(DetailReadMeterResponse.Data(imagePathL = fileUri,
                        imageFileL = selectedImageFile))
                    fotoAdapterLocationNF.notifyDataSetChanged()
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    private val startForProfileImageResultM =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK

                    val fileUri = data?.data!!
                    val selectedImageFile =
                        File(Objects.requireNonNull(FileUriUtils.getRealPath(this, fileUri)))
                    //                binding.fotoOrder.setImageURI(fileUri)
                    //                uploadFoto(fileUri)
                    fotoAdapterMeterNF.data.add(DetailReadMeterResponse.Data(imagePathM = fileUri,
                        imageFileM = selectedImageFile))
                    fotoAdapterMeterNF.notifyDataSetChanged()
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

//    private fun validate() : Boolean{
//        var allValid = true
//        val standMeter = binding.etPenggunaanMeter.text.toString().trim()
//        val nowMeter = binding.etNowMeter.text.toString().trim()
//
//        if (standMeter.isEmpty()){
//            binding.etPenggunaanMeter.error = "Kolom tidak boleh kosong"
//            binding.etPenggunaanMeter.requestFocus()
//            allValid = false
//        }
//        if (nowMeter.isEmpty()){
//            binding.etNowMeter.error = "Kolom tidak boleh kosong"
//            binding.etNowMeter.requestFocus()
//            allValid = false
//        }
//        return allValid
//    }

    private fun updateData() {
//        if (!validate()){
//            return
//        }
        sph = SharedPreference(this)
        val readMeterId = sph.fetchId()
        val jumlah = binding.etJumlah.text.toString().replace(".", "")
//        val standMeter = binding.etPenggunaanMeter.text.toString().trim()
        val nowMeter = binding.etNowMeter.text.toString().trim()
        var note = binding.desc.text.toString().trim()

        note = if (note.isEmpty()) {
            notes
        } else {
            binding.desc.text.toString().trim()
        }

        if (fotoAdapterLocationNF.data.size == 0) {
            Toast.makeText(this, "Upload bukti foto lokasi minimal 1", Toast.LENGTH_LONG).show()
            return
        }
        if (fotoAdapterMeterNF.data.size == 0) {
            Toast.makeText(this, "Upload bukti foto stand meter minimal 1", Toast.LENGTH_LONG)
                .show()
            return
        }

//        if (standMeter.isEmpty()){
//            binding.etPenggunaanMeter.error = "Kolom tidak boleh kosong"
//            binding.etPenggunaanMeter.requestFocus()
//            return
//        }
        if (nowMeter.isEmpty()) {
            binding.etNowMeter.error = "Kolom tidak boleh kosong"
            binding.etNowMeter.requestFocus()
            return
        }

        var imageBody: MutableList<MultipartBody.Part?>? = mutableListOf()
        val data = mutableMapOf<String, RequestBody>()
        if (fotoAdapterLocationNF.data.size > 0) {
            fotoAdapterLocationNF.data.forEachIndexed { index, result ->
                val requestFile: RequestBody? = result?.imageFileL?.asRequestBody()
                val img = MultipartBody.Part.createFormData("photo_location[$index]",
                    result?.imageFileL?.name,
                    requestFile!!)
                imageBody?.add(img)
            }
        }
//        else{
//            Toast.makeText(this, "Upload bukti foto lokasi minimal 1", Toast.LENGTH_LONG).show()
//        }
        if (fotoAdapterMeterNF.data.size > 0) {
            fotoAdapterMeterNF.data.forEachIndexed { index, result ->
                val requestFile: RequestBody? = result?.imageFileM?.asRequestBody()
                val img = MultipartBody.Part.createFormData("photo_stand_meter[$index]",
                    result?.imageFileM?.name,
                    requestFile!!)
                imageBody?.add(img)
            }
        }
//        else{
//            Toast.makeText(this, "Upload bukti foto stand meter minimal 1", Toast.LENGTH_LONG).show()
//        }
        data["read_meter_id"] = readMeterId!!.toRequestBody()
//        data["stand_meter_usage"] = standMeter.toRequestBody()
        data["current_meter_number"] = nowMeter.toRequestBody()
        data["billing_amount"] = jumlah.toRequestBody()
        data["notes"] = note.toRequestBody()

        ApiConfig.instanceRetrofit(this).updateRM(data, imageBody)
            .enqueue(object : Callback<UpdateDataResponse> {

                override fun onResponse(
                    call: Call<UpdateDataResponse>,
                    response: Response<UpdateDataResponse>,
                ) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        if (responseBody.statusCode == 200) {
                            Toast.makeText(this@ReadMeterNotFinished,
                                "Proses Berhasil",
                                Toast.LENGTH_LONG).show()

                            val intent = Intent()
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else {
                            response.errorBody()?.let { error ->
                                val parsing = Gson().fromJson(error.string(), UpdateDataResponse::class.java)
                                Toast.makeText(this@ReadMeterNotFinished, parsing.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        response.errorBody()?.let { error ->
                            val parsing = Gson().fromJson(error.string(), UpdateDataResponse::class.java)
                            Toast.makeText(this@ReadMeterNotFinished, parsing.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateDataResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}