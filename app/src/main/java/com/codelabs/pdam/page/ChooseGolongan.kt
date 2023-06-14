package com.codelabs.pdam.page

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.GolonganAdapter
import com.codelabs.pdam.adapter.ProvinceAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityChooseGolonganBinding
import com.codelabs.pdam.databinding.ActivityChooseProvinceBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.GolonganResponse
import com.codelabs.pdam.model.ProvinceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseGolongan : AppCompatActivity() {
    private lateinit var binding : ActivityChooseGolonganBinding
    var keyword = " "
    lateinit var sph : SharedPreference
    private lateinit var golonganAdapter : GolonganAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseGolonganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterGolongan()
        searchData()
        getListGolongan()
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
                getListGolongan()
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

    private fun setAdapterGolongan() {
        sph = SharedPreference(this)
        golonganAdapter = GolonganAdapter()
        binding.rvGolongan.setHasFixedSize(true)
        binding.rvGolongan.adapter = golonganAdapter
        binding.rvGolongan.layoutManager = LinearLayoutManager(this)

        golonganAdapter.onClick = {
            val data = Intent()
            data.putExtra("namegol", it!!.name.toString())
            data.putExtra("golid", it.golonganMeterId.toString())
            it.golonganMeterId?.let { it1 -> sph.saveIdGolongan(it1.toString()) }

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showGolongan(data: List<GolonganResponse.Data.Data?>) {
        golonganAdapter.setGolongan(data)
    }

//    private fun showProvince(data: List<ProvinceResponse.Data.Data?>) {
//        golonganAdapter.setProvince(data)
//    }

    private fun getListGolongan(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "100"
        param["sortBy"] = "name"
        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListGolongan(param).enqueue(object :
            Callback<GolonganResponse> {
            override fun onResponse(
                call: Call<GolonganResponse>,
                response: Response<GolonganResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showGolongan(it) }
                    }
                }
            }
            override fun onFailure(call: Call<GolonganResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}