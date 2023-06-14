package com.codelabs.pdam.page

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.DialogLogoutBinding
import com.codelabs.pdam.databinding.FragmentProfileBinding
import com.codelabs.pdam.model.Logout
import org.greenrobot.eventbus.EventBus

class LogoutDialog : DialogFragment() {

    private lateinit var binding : DialogLogoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DialogLogoutBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnLogout.setOnClickListener {
            EventBus.getDefault().post(Logout())
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(): DialogFragment {
            val dialog =  LogoutDialog()
            return dialog
        }
    }
}