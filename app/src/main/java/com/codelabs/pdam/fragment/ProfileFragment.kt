package com.codelabs.pdam.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.codelabs.pdam.R
import com.codelabs.pdam.api.ApiConfig
import com.codelabs.pdam.databinding.FragmentHistoryBinding
import com.codelabs.pdam.databinding.FragmentProfileBinding
import com.codelabs.pdam.helper.SharedPreference
import com.codelabs.pdam.model.Logout
import com.codelabs.pdam.model.ProfileResponse
import com.codelabs.pdam.page.ChangePassword
import com.codelabs.pdam.page.EditProfile
import com.codelabs.pdam.page.Login
import com.codelabs.pdam.page.LogoutDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    lateinit var sph : SharedPreference
    private lateinit var binding : FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setEvent()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setProfile(requireContext())
    }

    private fun setEvent() {
//        binding.layBack.setOnClickListener {
//
//        }
        binding.layEditProfile.setOnClickListener {
            startActivity(Intent(view?.context, EditProfile::class.java))
        }
        binding.layChangePass.setOnClickListener {
            startActivity(Intent(view?.context, ChangePassword::class.java))
        }
        binding.btnLogout.setOnClickListener {
            val dialog = LogoutDialog.newInstance()
            dialog.show(childFragmentManager, "")
//            startActivity(Intent(view?.context, Login::class.java))
//            activity?.finish()
        }
    }

    @Subscribe
    fun onLogout(data:Logout){
        sph = SharedPreference(requireContext())
        val intent = Intent(view?.context, Login::class.java)

        sph.put(false)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun setProfile(context: Context) {
        val image = binding.imageProfil
        sph = SharedPreference(context)

        ApiConfig.instanceRetrofit(view?.context!!).getProfile().enqueue(object :
            Callback<ProfileResponse> {

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
                            Glide.with(this@ProfileFragment)
                                .load(responseBody.data?.profile?.photo.toString())
                                .into(image)
                        }

                        binding.username.text = responseBody.data?.profile?.name.toString()
                        binding.role.text = responseBody.data?.role?.name.toString()
                        binding.email.text = responseBody.data?.email.toString()
                        binding.phone.text = responseBody.data?.profile?.phone.toString()

                        sph.saveEmail(responseBody.data?.email.toString())
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}