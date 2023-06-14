package com.codelabs.pdam.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.codelabs.pdam.R
import com.codelabs.pdam.adapter.LastHistoryAdapter
import com.codelabs.pdam.adapter.ReadMeterFinishedAdapter
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.FragmentHomeBinding
import com.codelabs.pdam.databinding.FragmentProfileBinding
import com.codelabs.pdam.eventbus.DataSelected
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.ProfileResponse
import com.codelabs.pdam.model.ReadMeterResponse
import com.codelabs.pdam.page.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var sph : SharedPreference
    private lateinit var binding : FragmentHomeBinding
    private lateinit var lastHadapter : LastHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val context = requireContext()
        setEvent()
        setAdapterLastH(context)
        lastHistory(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setProfile()
        setAdapterLastH(requireContext())
        lastHistory(requireContext())
    }

    private fun setEvent() {
        binding.readMeter.setOnClickListener {
            startActivity(Intent(view?.context, BacaMeter::class.java))
        }
        binding.newInstallation.setOnClickListener {
            startActivity(Intent(view?.context, NewInstallation::class.java))
        }
        binding.complain.setOnClickListener {
            startActivity(Intent(view?.context, EComplain::class.java))
        }
        binding.history.setOnClickListener {
            startActivity(Intent(view?.context, History::class.java))
        }
        binding.infoPdam.setOnClickListener {
            startActivity(Intent(view?.context, PdamInformation::class.java))
        }
        binding.infoCust.setOnClickListener {
            startActivity(Intent(view?.context, CustomerInformation::class.java))
        }
    }

    private fun setAdapterLastH(context: Context) {
        lastHadapter = LastHistoryAdapter()
        binding.rvLastHistory.setHasFixedSize(true)
        binding.rvLastHistory.adapter = lastHadapter
        binding.rvLastHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        lastHadapter.onClick = {
            EventBus.getDefault().post(DataSelected(it))
            startActivity(Intent(context, ReadMeterFinished::class.java))
            it?.readMeterId?.let { it1 -> sph.saveId(it1.toString()) }
        }
    }

    private fun showDataLastH(data: List<ReadMeterResponse.Data.Data?>) {
        lastHadapter.setDataLastHistory(data)
    }

    private fun setProfile() {
        sph = SharedPreference(view?.context!!)
        val image = binding.imageProfil

        ApiConfig.instanceRetrofit(view?.context!!).getProfile().enqueue(object : Callback<ProfileResponse>{

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>,
            ) {
                if (response.code() == 402) {
                    Toast.makeText(view?.context, "Sesi berakhir, silahkan melakukan login ulang", Toast.LENGTH_LONG).show()
                    startActivity(Intent(view?.context, Login::class.java))
                    sph.put(false)
                    activity?.finish()
                }
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.statusCode == 200) {
                        if (activity != null){
                            Glide.with(this@HomeFragment)
                                .load(responseBody.data?.profile?.photo.toString())
                                .into(image)
                        }

                        binding.username.text = responseBody.data?.profile?.name.toString()
                        binding.role.text = responseBody.data?.role?.name.toString()

//                        responseBody.data?.let { showDataLastH(it) }
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun lastHistory(context: Context){
        sph = SharedPreference(context)
        val param = mutableMapOf<String, String>()
        param["page"] = "1"
        param["limit"] = "10"
        param["status"] = "1"

        ApiConfig.instanceRetrofit(context).getList(param).enqueue(object : Callback<ReadMeterResponse>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ReadMeterResponse>,
                response: Response<ReadMeterResponse>,
            ) {
                val responseBody = response.body()

                if (responseBody != null){
                    if (responseBody.statusCode == 200){
                        responseBody.data?.data?.let { showDataLastH(it) }
                        lastHadapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<ReadMeterResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

//    @Subscribe
}