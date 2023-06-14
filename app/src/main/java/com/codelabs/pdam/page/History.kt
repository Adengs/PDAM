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
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.CustomerInformationAdapter
import com.codelabs.pdam.adapter.HistoryAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.ActivityHistoryBinding
import com.codelabs.pdam.eventbus.CustomerSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.CustomerResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class History : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    private lateinit var customerAdapter : HistoryAdapter
    lateinit var sph : SharedPreference
    var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
        setAdapterCust()
        getListCust()
        searchData()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setAdapterCust() {
        customerAdapter = HistoryAdapter()
        binding.rvItemHistory.setHasFixedSize(true)
        binding.rvItemHistory.adapter = customerAdapter
        binding.rvItemHistory.layoutManager = LinearLayoutManager(this)

        customerAdapter.onClick = {
            EventBus.getDefault().post(CustomerSelected(it))
            val intent = Intent(this, HistoryDetail::class.java)
            intent.putExtra(HistoryDetail.CUSTOMER_NAME, it?.name)
            intent.putExtra(HistoryDetail.CUSTOMER_ID, it?.customerId.toString())
            intent.putExtra(HistoryDetail.CUSTOMER_ADDRESS, it?.address)
            startActivity(intent)
            it?.customerId?.let { it1 -> sph.saveId(it1.toString()) }
        }
    }

    private fun showDataCust(data: List<CustomerResponse.Data.Data?>) {
        customerAdapter.setDataCust(data)
    }

    private fun getListCust(){
        sph = SharedPreference(this)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "10"
//        param["sortBy"] = "name"
//        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(this).getListCust(param).enqueue(object :
            Callback<CustomerResponse> {
            override fun onResponse(
                call: Call<CustomerResponse>,
                response: Response<CustomerResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showDataCust(it) }
                    }
                }
            }
            override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
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
                getListCust()
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