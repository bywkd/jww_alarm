package com.jww.alarm.views.alarmListView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jww.alarm.MainActivity
import com.jww.alarm.bases.BaseFragment
import com.jww.alarm.databinding.FragmentAlarmListBinding
import com.jww.alarm.models.AlarmItemModel

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

    private lateinit var adapter: AlarmListAdapter

    private lateinit var currentAct: MainActivity

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
        init()
    }


    private fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = AlarmListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(currentAct)
        val model = AlarmItemModel()
        val data =
            arrayListOf(model, model, model, model, model, model, model, model)
        adapter.addAllData(data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentAct = (context as MainActivity)
    }
}