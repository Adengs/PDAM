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
import com.codelabs.pdam.adapter.CustomerContactAdapter
import com.codelabs.pdam.adapter.ProvinceAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityChooseProvinceBinding
import com.codelabs.pdam.eventbus.CustomerSelected
import com.codelabs.pdam.eventbus.ProvinceSelect
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.model.ProvinceResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseProvince : AppCompatActivity() {
    private lateinit var binding : ActivityChooseProvinceBinding
    var keyword = " "
    lateinit var sph : SharedPreference
    private lateinit var provinceAdapter : ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterProvince()
        searchData()
        getListProvince()
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
                getListProvince()
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

    private fun setAdapterProvince() {
        sph = SharedPreference(this)
        provinceAdapter = ProvinceAdapter()
        binding.rvProvince.setHasFixedSize(true)
        binding.rvProvince.adapter = provinceAdapter
        binding.rvProvince.layoutManager = LinearLayoutManager(this)

        provinceAdapter.onClick = {

            val data = Intent()
            data.putExtra("nameprov", it!!.name.toString())
            data.putExtra("idprov", it.provinceId.toString())
            it.provinceId?.let { it1 -> sph.saveIdProv(it1.toString()) }

            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showProvince(data: List<ProvinceResponse.Data.Data?>) {
        provinceAdapter.setProvince(data)
    }

    private fun getListProvince(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "100"
        param["sortBy"] = "name"
        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListProvince(param).enqueue(object :
            Callback<ProvinceResponse> {
            override fun onResponse(
                call: Call<ProvinceResponse>,
                response: Response<ProvinceResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showProvince(it) }
                    }
                }
            }
            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}