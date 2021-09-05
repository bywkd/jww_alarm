package com.jww.alarm.views.registerAlarmView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentRegisterAlarmBinding

class RegisterAlarmFragment : BaseFragment() {

    companion object {
        fun newInstance(): RegisterAlarmFragment {
            return RegisterAlarmFragment().apply {
                val arguments = Bundle().apply {
                    putString("", "")
                }
                this.arguments = arguments
            }
        }
    }

    private var _binding: FragmentRegisterAlarmBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var vm: RegisterAlarmVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterAlarmBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, RegisterAlarmVM.VMF()).get(RegisterAlarmVM::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}