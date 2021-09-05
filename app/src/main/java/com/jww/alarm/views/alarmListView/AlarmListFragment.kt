package com.jww.alarm.views.alarmListView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentAlarmListBinding

class AlarmListFragment : BaseFragment() {
    companion object {
        fun newInstance(): AlarmListFragment {
            return AlarmListFragment().apply {
                val arguments = Bundle().apply {
                    putString("", "")
                }
                this.arguments = arguments
            }
        }
    }

    private var _binding: FragmentAlarmListBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var vm: AlarmListVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmListBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, AlarmListVM.VMF()).get(AlarmListVM::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}