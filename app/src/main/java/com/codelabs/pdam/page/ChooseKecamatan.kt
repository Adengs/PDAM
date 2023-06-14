package com.codelabs.pdam.page

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.KabupatenAdapter
import com.codelabs.pdam.adapter.KecamatanAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityChooseKecamatanBinding
import com.codelabs.pdam.eventbus.KabupatenSelect
import com.codelabs.pdam.eventbus.KecamatanSelect
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.KabupatenResponse
import com.codelabs.pdam.model.KecamatanResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseKecamatan : AppCompatActivity() {
    private lateinit var binding : ActivityChooseKecamatanBinding
    var keyword = " "
    lateinit var sph : SharedPreference
    private lateinit var kecamatanAdapter : KecamatanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseKecamatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterKecamatan()
        searchData()
        getListKecamatan()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun searchData(){
        binding.etSearch.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keyword = char.toString()
                getListKecamatan()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH){
                    binding.etSearch.hideKeyboard()
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

    private fun setAdapterKecamatan() {
        sph = SharedPreference(this)
        kecamatanAdapter = KecamatanAdapter()
        binding.rvKecamatan.setHasFixedSize(true)
        binding.rvKecamatan.adapter = kecamatanAdapter
        binding.rvKecamatan.layoutManager = LinearLayoutManager(this)

        kecamatanAdapter.onClick = {

            val data = Intent()
            data.putExtra("namekec", it!!.name.toString())
            data.putExtra("idkec", it.code)
            it.code?.let { it1 -> sph.saveIdKec(it1) }

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showKecamatan(data: List<KecamatanResponse.Data.Data?>) {
        kecamatanAdapter.setKecamatan(data)
    }

    private fun getListKecamatan(){
        sph = SharedPreference(this)
        val id = sph.fetchIdKab().toString()
        val param = mutableMapOf<String, String>()
        param["city_id"] = id
        param["page"] = "1"
        param["limit"] = "100"
        param["sortBy"] = "name"
        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListKecamatan(param).enqueue(object :
            Callback<KecamatanResponse> {
            override fun onResponse(
                call: Call<KecamatanResponse>,
                response: Response<KecamatanResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showKecamatan(it) }
                    }
                }
            }
            override fun onFailure(call: Call<KecamatanResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}