package com.codelabs.pdam.page

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityNewInstallationBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.DetailReadMeterResponse
import com.codelabs.pdam.model.NewInstallationResponse
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.google.gson.Gson
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NewInstallation : AppCompatActivity() {
    private lateinit var binding: ActivityNewInstallationBinding
    lateinit var sph: SharedPreference
    var idGol = " "
    var idKab = " "
    var idKec = " "
    var notes = "Tidak ada catatan"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewInstallationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val dateTime = getCurrentDateTime()
        val dateInString = dateTime.toString("dd-MMMM-yyyy")
        binding.etDateRegist.setText(dateInString.replace("-", " "))

        setEvent()
        showData()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.layNewInstallation.setOnClickListener {
            binding.layNewInstallation.hideKeyboard()
        }
        binding.etDateRegist.setOnClickListener {
//            showCalender()
        }

        binding.etGolonganMeter.setOnClickListener {
            val requestCode = 1
            val intent = Intent(this, ChooseGolongan::class.java)
            startActivityForResult(intent, requestCode)
        }

        binding.etUkuranMeter.setOnClickListener {
            val requestCode = 2
            val intent = Intent(this, ChooseUkuran::class.java)
            startActivityForResult(intent, requestCode)
        }


//        binding.etProvince.setOnClickListener {
//            val requestCode = 1
//            val intent = Intent(this, ChooseProvince::class.java)
//            startActivityForResult(intent, requestCode)
//        }

//        binding.etKabupaten.setOnClickListener {
//            val province = binding.etProvince.text.toString().trim()
//            if (province.isEmpty()) {
//                Toast.makeText(this, "Silahkan pilih provinsi terlebih dahulu", Toast.LENGTH_LONG)
//                    .show()
//            } else {
//                val requestCode = 2
//                val intent = Intent(this, ChooseKabupaten::class.java)
//                startActivityForResult(intent, requestCode)
//            }
//        }
//        binding.etKecamatan.setOnClickListener {
//            val province = binding.etProvince.text.toString().trim()
//            val kabupaten = binding.etKabupaten.text.toString().trim()
//            if (province.isEmpty()) {
//                Toast.makeText(this, "Silahkan pilih provinsi terlebih dahulu", Toast.LENGTH_LONG)
//                    .show()
//            } else if (kabupaten.isEmpty()) {
//                Toast.makeText(this, "Silahkan pilih kabupaten terlebih dahulu", Toast.LENGTH_LONG)
//                    .show()
//            } else {
//                val requestCode = 3
//                val intent = Intent(this, ChooseKecamatan::class.java)
//                startActivityForResult(intent, requestCode)
//            }
//
//        }

//        binding.layUpload.setOnClickListener {
//            ImagePicker.with(this)
//                .crop()
//                .compress(1024)
//                .maxResultSize(1080, 1080)
//                .createIntent { intent ->
//                    startForKTPImageResult.launch(intent)
//                }
//            binding.textFile.visibility = View.VISIBLE
//            binding.layUpload.visibility = View.GONE
//        }

        binding.btnSend.setOnClickListener {
            newInstallation()
        }
    }

//    private fun showCalender(){
//        val calender = Calendar.getInstance()
//        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofmonth ->
//            calender.set(Calendar.YEAR, year)
//            calender.set(Calendar.MONTH, month)
//            calender.set(Calendar.DAY_OF_MONTH, dayofmonth)
//
//        }
//        DatePickerDialog(this, datePicker, calender.get(Calendar.YEAR),
//        calender.get(Calendar.MONTH),
//        calender.get(Calendar.DAY_OF_MONTH)).show()
//
//        val dateFormat = "dd-MMMM-yyyy"
//        val sdf = SimpleDateFormat(dateFormat, Locale("in", "ID"))
//        binding.etDateRegist.setText(sdf.format(calender.time))
//    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun Date.toString(format: String, locale: Locale = Locale("in", "ID")): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                binding.etGolonganMeter.setText(data!!.getStringExtra("namegol"))
                idGol = data.getStringExtra("golid").toString()
                binding.etKabupaten.isClickable = true

                data.getStringExtra("namegol")?.let { Log.e("cek golongan", it) }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                binding.etUkuranMeter.setText("${data!!.getStringExtra("size")} ${data.getStringExtra("unit")}")
                idGol = data.getStringExtra("sizeid").toString()
                binding.etKabupaten.isClickable = true

                data.getStringExtra("size")?.let { Log.e("cek golongan", it) }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_OK) {
//                binding.etProvince.setText(data!!.getStringExtra("nameprov"))
//                idProv = data.getStringExtra("idprov").toString()
//                binding.etKabupaten.isClickable = true
//            } else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }
//        if (requestCode == 2) {
//            if (resultCode == Activity.RESULT_OK) {
//                binding.etKabupaten.setText(data!!.getStringExtra("namekab"))
//                idKab = data.getStringExtra("idkab").toString()
//                binding.etKecamatan.isClickable = true
//            } else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }
//        if (requestCode == 3) {
//            if (resultCode == Activity.RESULT_OK) {
//                binding.etKecamatan.setText(data!!.getStringExtra("namekec"))
//                idKec = data.getStringExtra("idkec").toString()
//            } else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }

    }

//    @SuppressLint("NotifyDataSetChanged")
//    private val startForKTPImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            val resultCode = result.resultCode
//            val data = result.data
//
//            when (resultCode) {
//                Activity.RESULT_OK -> {
//                    //Image Uri will not be null for RESULT_OK
//
//                    val fileUri = data?.data!!
////                    val fileName = data.data.toString()
////                    val selectedImageFile =
////                        File(Objects.requireNonNull(FileUriUtils.getRealPath(this, fileUri)))
//                    binding.textFile.visibility = View.VISIBLE
//                    binding.layUpload.visibility = View.GONE
//                    binding.textFile.text = fileUri.toString()
//                    Log.e("cek file", fileUri.toString())
//                    //                uploadFoto(fileUri)
////                    fotoAdapterLocationNF.data.add(DetailReadMeterResponse.Data(imagePathL = fileUri,
////                        imageFileL = selectedImageFile))
////                    fotoAdapterLocationNF.notifyDataSetChanged()
//                }
//                ImagePicker.RESULT_ERROR -> {
//                    binding.textFile.visibility = View.VISIBLE
//                    binding.layUpload.visibility = View.GONE
//                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
//                }
//                else -> {
//                    binding.textFile.visibility = View.VISIBLE
//                    binding.layUpload.visibility = View.GONE
//                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

    private fun showData() {
        sph = SharedPreference(this)
        val prov = sph.fetchProvince()
        val kab = sph.fetchKabupaten()
        val kec = sph.fetchKecamtan()

        binding.etProvince.setText(prov)
        binding.etKabupaten.setText(kab)
        binding.etKecamatan.setText(kec)
    }

    private fun newInstallation() {
        sph = SharedPreference(this)
        val name = binding.etNameCust.text.toString().trim()
        val address = binding.etAddressCust.text.toString().trim()
        val contact = binding.etContactCust.text.toString().trim()
        val golongan = sph.fetchIdGolongan()!!
        val ukuran = sph.fetchIdUkuran()!!
        val province = sph.fetchIdProv()!!
        val kabupaten = sph.fetchIdKab()!!
        val kecamatan = sph.fetchIdKec()!!
        val postalCode = binding.etPosCode.text.toString().trim()
        val addresses = binding.etAddresses.text.toString().trim()
        val nik = binding.etNik.text.toString().trim()
//        val luas = binding.etLuas.text.toString().trim()
//        val panjang = binding.etPanjang.text.toString().trim()
//        val lebar = binding.etLebar.text.toString().trim()
//        val luasBangunan = binding.etLuasBangunan.text.toString().trim()
//        val panjangBangunan = binding.etPanjangBangunan.text.toString().trim()
//        val lebarBangunan = binding.etLebarBangunan.text.toString().trim()
//        val desc = binding.desc.text.toString().trim()
        var note = binding.desc.text.toString().trim()

        //validation
        if (name.isEmpty()) {
            binding.etNameCust.error = "Nama pelanggan tidak boleh kosong"
            binding.etNameCust.requestFocus()
            return
        }
        if (address.isEmpty()) {
            binding.etAddressCust.error = "Alamat pelanggan tidak boleh kosong"
            binding.etAddressCust.requestFocus()
            return
        }
        if (contact.isEmpty()) {
            binding.etContactCust.error = "Kontak pelanggan tidak boleh kosong"
            binding.etContactCust.requestFocus()
            return
        }
        if (binding.etGolonganMeter.text.toString().isEmpty()) {
            Toast.makeText(this, "Golongan meter tidak boleh kosong", Toast.LENGTH_LONG).show()
            return
        }
        if (binding.etUkuranMeter.text.toString().isEmpty()) {
            Toast.makeText(this, "Ukuran meter tidak boleh kosong", Toast.LENGTH_LONG).show()
            return
        }
//        if (province.isEmpty()){
//            Toast.makeText(this, "Provinsi tidak boleh kosong", Toast.LENGTH_LONG).show()
//            return
//        }
//        if (kabupaten.isEmpty()){
//            Toast.makeText(this, "Kabupaten tidak boleh kosong", Toast.LENGTH_LONG).show()
//            return
//        }
//        if (kecamatan.isEmpty()){
//            Toast.makeText(this, "Kecamatan tidak boleh kosong", Toast.LENGTH_LONG).show()
//            return
//        }
        if (postalCode.isEmpty()) {
            binding.etPosCode.error = "Kode pos tidak boleh kosong"
            binding.etPosCode.requestFocus()
            return
        }
        if (addresses.isEmpty()) {
            binding.etAddresses.error = "Alamat lengkap tidak boleh kosong"
            binding.etAddresses.requestFocus()
            return
        }
        if (nik.isEmpty()) {
            binding.etNik.error = "NIK tidak boleh kosong"
            binding.etNik.requestFocus()
            return
        }
//        if (luas.isEmpty()){
//            binding.etLuas.error = "Luas tanah tidak boleh kosong"
//            binding.etLuas.requestFocus()
//            return
//        }
//        if (panjang.isEmpty()){
//            binding.etPanjang.error = "Panjang tanah tidak boleh kosong"
//            binding.etPanjang.requestFocus()
//            return
//        }
//        if (lebar.isEmpty()){
//            binding.etLebar.error = "Lebar tanah tidak boleh kosong"
//            binding.etLebar.requestFocus()
//            return
//        }
//        if (luasBangunan.isEmpty()){
//            binding.etLuasBangunan.error = "Luas bangunan tidak boleh kosong"
//            binding.etLuasBangunan.requestFocus()
//            return
//        }
//        if (panjangBangunan.isEmpty()){
//            binding.etPanjangBangunan.error = "Panjang bangunan tidak boleh kosong"
//            binding.etPanjangBangunan.requestFocus()
//            return
//        }
//        if (lebarBangunan.isEmpty()){
//            binding.etLebarBangunan.error = "Lebar bangunan tidak boleh kosong"
//            binding.etLebarBangunan.requestFocus()
//            return
//        }

        //validasi karakter
        if (name.isNotEmpty() && name.length < 2) {
            binding.etNameCust.error = "Nama tidak boleh kurang dari 2 karakter"
            binding.etNameCust.requestFocus()
            return
        }
        if (contact.isNotEmpty() && contact.length < 9) {
            binding.etContactCust.error = "Kontak tidak boleh kurang dari 9 karakter"
            binding.etContactCust.requestFocus()
            return
        }
        if (nik.isNotEmpty() && nik.length < 15) {
            binding.etNik.error = "NIK tidak boleh kurang dari 15 karakter"
            binding.etNik.requestFocus()
            return
        }
        if (nik.isNotEmpty() && nik.length > 16) {
            binding.etNik.error = "NIK tidak boleh lebih dari 16 karakter"
            binding.etNik.requestFocus()
            return
        }
        if (postalCode.isNotEmpty() && postalCode.length > 5) {
            binding.etPosCode.error = "Kode pos tidak boleh lebih dari 5 karakter"
            binding.etPosCode.requestFocus()
            return
        }
//        if (postalCode.isNotEmpty() && postalCode.length > 5){
//            binding.etPosCode.error = "Kode pos tidak boleh lebih dari 5 karakter"
//            binding.etPosCode.requestFocus()
//            return
//        }


//        note = if (note.isEmpty()){
//            notes
//        }else{
//            binding.desc.text.toString().trim()
//        }

        val data = mutableMapOf<String, RequestBody>()
        data["name"] = name.toRequestBody()
        data["address"] = address.toRequestBody()
        data["phone"] = contact.toRequestBody()
        data["golongan_meter_id"] = golongan.toRequestBody()
        data["ukuran_meter_id"] = ukuran.toRequestBody()
        data["province_id"] = province.toRequestBody()
        data["city_id"] = kabupaten.toRequestBody()
        data["district_id"] = kecamatan.toRequestBody()
        data["postal_code"] = postalCode.toRequestBody()
        data["address_location"] = addresses.toRequestBody()
        data["nik"] = nik.toRequestBody()
//        data["land_area"] = luas.toRequestBody()
//        data["land_length"] = panjang.toRequestBody()
//        data["land_breadth"] = lebar.toRequestBody()
//        data["building_area"] = luasBangunan.toRequestBody()
//        data["building_length"] = panjangBangunan.toRequestBody()
//        data["building_breadth"] = lebarBangunan.toRequestBody()
        data["note"] = note.toRequestBody()

        ApiConfig.instanceRetrofit(this).newInstallation(data)
            .enqueue(object : Callback<NewInstallationResponse> {

                override fun onResponse(
                    call: Call<NewInstallationResponse>,
                    response: Response<NewInstallationResponse>,
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.statusCode == 200) {
                            Toast.makeText(this@NewInstallation,
                                responseBody.message.toString(),
                                Toast.LENGTH_LONG).show()
                            finish()
                            onBackPressed()
                        } else {
                            Toast.makeText(this@NewInstallation,
                                responseBody.message.toString(),
                                Toast.LENGTH_LONG).show()
//                        response.errorBody()?.let { error ->
//                            val parsing =
//                                Gson().fromJson(error.string(), NewInstallationResponse::class.java)
//                            Toast.makeText(this@NewInstallation, parsing.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
                        }
                    } else {
                        Toast.makeText(this@NewInstallation,
                            responseBody?.message.toString(),
                            Toast.LENGTH_LONG).show()
//                    response.errorBody()?.let { error ->
//                        val parsing =
//                            Gson().fromJson(error.string(), NewInstallationResponse::class.java)
//                        Toast.makeText(this@NewInstallation, parsing.message, Toast.LENGTH_LONG)
//                            .show()
//                    }
                    }
                }

                override fun onFailure(call: Call<NewInstallationResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}