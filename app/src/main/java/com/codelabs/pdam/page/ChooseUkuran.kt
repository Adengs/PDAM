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
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.GolonganAdapter
import com.codelabs.pdam.adapter.UkuranAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityChooseGolonganBinding
import com.codelabs.pdam.databinding.ActivityChooseUkuranBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.GolonganResponse
import com.codelabs.pdam.model.UkuranResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseUkuran : AppCompatActivity() {
    private lateinit var binding : ActivityChooseUkuranBinding
    var keyword = " "
    lateinit var sph : SharedPreference
    private lateinit var ukuranAdapter : UkuranAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseUkuranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterUkuran()
        searchData()
        getListUkuran()
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
                getListUkuran()
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

    private fun setAdapterUkuran() {
        sph = SharedPreference(this)
        ukuranAdapter = UkuranAdapter()
        binding.rvUkuran.setHasFixedSize(true)
        binding.rvUkuran.adapter = ukuranAdapter
        binding.rvUkuran.layoutManager = LinearLayoutManager(this)

        ukuranAdapter.onClick = {
            val data = Intent()
            data.putExtra("size", it!!.size.toString())
            data.putExtra("unit", it!!.unit.toString())
            data.putExtra("sizeid", it.ukuranMeterId.toString())
            it.ukuranMeterId?.let { it1 -> sph.saveIdUkuran(it1.toString()) }
            Log.e("cek klik", it.size.toString())

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showUkuran(data: List<UkuranResponse.Data.Data?>) {
        ukuranAdapter.setUkuran(data)
    }

//    private fun showProvince(data: List<ProvinceResponse.Data.Data?>) {
//        golonganAdapter.setProvince(data)
//    }

    private fun getListUkuran(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "100"
        param["sortBy"] = "size"
        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListUkuran(param).enqueue(object :
            Callback<UkuranResponse> {
            override fun onResponse(
                call: Call<UkuranResponse>,
                response: Response<UkuranResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showUkuran(it) }
                    }
                }
            }
            override fun onFailure(call: Call<UkuranResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}