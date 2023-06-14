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
import com.codelabs.pdam.adapter.ProvinceAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityChooseKabupatenBinding
import com.codelabs.pdam.eventbus.KabupatenSelect
import com.codelabs.pdam.eventbus.ProvinceSelect
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.KabupatenResponse
import com.codelabs.pdam.model.ProvinceResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseKabupaten : AppCompatActivity() {
    private lateinit var binding : ActivityChooseKabupatenBinding
    var keyword = " "
    lateinit var sph : SharedPreference
    private lateinit var kabupatenAdapter : KabupatenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseKabupatenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterKabupaten()
        searchData()
        getListKabupaten()
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
                getListKabupaten()
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

    private fun setAdapterKabupaten() {
        sph = SharedPreference(this)
        kabupatenAdapter = KabupatenAdapter()
        binding.rvKabupaten.setHasFixedSize(true)
        binding.rvKabupaten.adapter = kabupatenAdapter
        binding.rvKabupaten.layoutManager = LinearLayoutManager(this)

        kabupatenAdapter.onClick = {
//            EventBus.getDefault().post(KabupatenSelect(it))
//            it?.name?.let { it1 -> sph.saveKabupaten(it1) }
//            it?.cityId?.let { it1 -> sph.saveIdKab(it1.toString()) }
//            onBackPressed()

            val data = Intent()
            data.putExtra("namekab", it!!.name.toString())
            data.putExtra("idkab", it.code.toString())
            it.code?.let { it1 -> sph.saveIdKab(it1.toString()) }

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showKabupaten(data: List<KabupatenResponse.Data.Data?>) {
        kabupatenAdapter.setKabupaten(data)
    }

    private fun getListKabupaten(){
        sph = SharedPreference(this)
        val id = sph.fetchIdProv().toString()
        val param = mutableMapOf<String, String>()
        param["province_id"] = id
        param["page"] = "1"
        param["limit"] = "100"
        param["sortBy"] = "name"
        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListKabupaten(param).enqueue(object :
            Callback<KabupatenResponse> {
            override fun onResponse(
                call: Call<KabupatenResponse>,
                response: Response<KabupatenResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showKabupaten(it) }
                    }
                }
            }
            override fun onFailure(call: Call<KabupatenResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}