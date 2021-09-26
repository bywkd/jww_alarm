package com.jww.alarm.views.alarmListView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.jww.alarm.databinding.ItemAlarmListBinding
import com.jww.alarm.models.AlarmItemModel


class AlarmListAdapter : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    val dataList: ArrayList<AlarmItemModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListAdapter.ViewHolder {
        val binding =
            ItemAlarmListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addAllData(dataList: ArrayList<AlarmItemModel>) {
        if (this.dataList.size > 0) {
            this.dataList.clear()
        }
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val bind: ItemAlarmListBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: AlarmItemModel) {
            bind.tvAmAndPm.text = if (data.hour > 12) "PM" else "AM"
            bind.tvHour.text = data.hour.toString()
            bind.tvMin.text = data.min.toString()
            bind.tvTime.text = String.format("${data.year}.${data.month}.${data.dayOfMonth}")
            bind.tvSoundFileName.text = data.soundFileName

            if (data.sound) {

            } else {

            }

            if (data.vibration) {

            } else {

            }
            bind.swActive.isChecked = data.isActive
            bind.swActive.setOnCheckedChangeListener { compoundButton, b ->
                Log.d("Won", "활성화 $b")
                if (b) {

                } else {

                }
            }
        }
    }
}