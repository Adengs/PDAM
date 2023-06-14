package com.codelabs.pdam.page

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.ReadMeterFinishedAdapter
import com.codelabs.pdam.adapter.ReadMeterNotFinishedAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityBacaMeterBinding
import com.codelabs.pdam.eventbus.DataSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.DetailReadMeterResponse
import com.codelabs.pdam.model.ReadMeterResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BacaMeter : AppCompatActivity() {

    lateinit var sph : SharedPreference
    private lateinit var binding : ActivityBacaMeterBinding
    private lateinit var rmNFadapter : ReadMeterNotFinishedAdapter
    private lateinit var rmFadapter : ReadMeterFinishedAdapter
    var page = 0
    var keyword = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBacaMeterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        changeTab()
        setEvent()
        setAdapterNF()
        setAdapterF()
        searchData()
    }

    override fun onResume() {
        super.onResume()
        changeTab()
        setAdapterNF()
        setAdapterF()
        searchData()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
//        binding.etSearch.
        binding.layNotFinished.setOnClickListener {
            page = 0
            changeTab()
        }
        binding.layFinished.setOnClickListener {
            page = 1
            changeTab()
        }
    }

    private fun changeTab(){
        if (page == 0){
            binding.textNotFinished.setTextColor(ContextCompat.getColor(this, R.color.blue_pdam))
            binding.line1.setImageResource(R.drawable.line_view_blue)
            binding.textFinished.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.line2.setImageResource(R.drawable.line_view_white)

            binding.rvNotFinished.visibility = View.VISIBLE
            binding.rvFinished.visibility = View.GONE

            getListNF()
        }
        if (page == 1){
            binding.textFinished.setTextColor(ContextCompat.getColor(this, R.color.blue_pdam))
            binding.line2.setImageResource(R.drawable.line_view_blue)
            binding.textNotFinished.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.line1.setImageResource(R.drawable.line_view_white)

            binding.rvNotFinished.visibility = View.GONE
            binding.rvFinished.visibility = View.VISIBLE

            getListF()
        }
    }

    private fun setAdapterNF() {
        rmNFadapter = ReadMeterNotFinishedAdapter()
        binding.rvNotFinished.setHasFixedSize(true)
        binding.rvNotFinished.adapter = rmNFadapter
        binding.rvNotFinished.layoutManager = LinearLayoutManager(this)

        rmNFadapter.onClick = {
            EventBus.getDefault().post(DataSelected(it))
            startActivity(Intent(this, ReadMeterNotFinished::class.java))
            it?.readMeterId?.let { it1 -> sph.saveId(it1.toString()) }
        }
    }

    private fun setAdapterF() {
        rmFadapter = ReadMeterFinishedAdapter()
        binding.rvFinished.setHasFixedSize(true)
        binding.rvFinished.adapter = rmFadapter
        binding.rvFinished.layoutManager = LinearLayoutManager(this)

        rmFadapter.onClick = {
            EventBus.getDefault().post(DataSelected(it))
            startActivity(Intent(this, ReadMeterFinished::class.java))
            it?.readMeterId?.let { it1 -> sph.saveId(it1.toString()) }
        }
    }

    private fun showDataNF(data: List<ReadMeterResponse.Data.Data?>) {
        rmNFadapter.setDataNotFinished(data)
    }

    private fun showDataF(data: List<ReadMeterResponse.Data.Data?>) {
        rmFadapter.setDataFinished(data)
    }

    private fun getListNF(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "10"
        param["keyword"] = keyword
        param["status"] = "0"

        ApiConfig.instanceRetrofit(this).getList(param).enqueue(object : Callback<ReadMeterResponse>{
            override fun onResponse(
                call: Call<ReadMeterResponse>,
                response: Response<ReadMeterResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showDataNF(it) }
                        binding.rvNotFinished.visibility = View.VISIBLE
                        binding.rvFinished.visibility = View.GONE
                    }
                }
            }
            override fun onFailure(call: Call<ReadMeterResponse>, t: Throwable) {
               t.printStackTrace()
            }

        })
    }

    private fun getListF(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "10"
        param["keyword"] = keyword
        param["status"] = "1"

        ApiConfig.instanceRetrofit(this).getList(param).enqueue(object : Callback<ReadMeterResponse>{
            override fun onResponse(
                call: Call<ReadMeterResponse>,
                response: Response<ReadMeterResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showDataF(it) }
                        binding.rvNotFinished.visibility = View.GONE
                        binding.rvFinished.visibility = View.VISIBLE
                    }
                }
            }
            override fun onFailure(call: Call<ReadMeterResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun searchData(){
        binding.etSearch.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keyword = char.toString()
                if (page == 0){
                    getListNF()
                }
                if (page == 1){
                    getListF()
                }
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
}