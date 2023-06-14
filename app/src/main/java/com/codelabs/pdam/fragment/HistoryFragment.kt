package com.codelabs.pdam.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.HistoryAdapter
import com.codelabs.pdam.adapter.LastHistoryAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.FragmentHistoryBinding
import com.codelabs.pdam.databinding.FragmentHomeBinding
import com.codelabs.pdam.eventbus.CustomerSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.CustomerResponse
import com.codelabs.pdam.page.HistoryDetail
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    lateinit var sph : SharedPreference
    private lateinit var binding : FragmentHistoryBinding
    private lateinit var customerAdapter : HistoryAdapter
    var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val context = requireContext()
        setAdapterCust(context)
        getListCust(context)
        searchData(context)

        return binding.root
    }

    private fun setAdapterCust(context: Context) {
        customerAdapter = HistoryAdapter()
        binding.rvItemHistory.setHasFixedSize(true)
        binding.rvItemHistory.adapter = customerAdapter
        binding.rvItemHistory.layoutManager = LinearLayoutManager(context)

        customerAdapter.onClick = {
            EventBus.getDefault().post(CustomerSelected(it))
            val intent = Intent(context, HistoryDetail::class.java)
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

    private fun getListCust(context: Context){
        sph = SharedPreference(context)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "10"
//        param["sortBy"] = "name"
//        param["sort"] = "asc"
        param["keyword"] = keyword

        ApiConfig.instanceRetrofit(context).getListCust(param).enqueue(object :
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

    private fun searchData(context: Context){
        binding.etSearch.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                keyword = char.toString()
                getListCust(context)
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