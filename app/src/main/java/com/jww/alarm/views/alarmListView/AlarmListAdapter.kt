package com.jww.alarm.views.alarmListView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.jww.alarm.R
import com.jww.alarm.databinding.ItemAlarmListBinding
import com.jww.alarm.db.AlarmDataEntity
import com.jww.alarm.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AlarmListAdapter : RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    val dataList: ArrayList<AlarmDataEntity> = arrayListOf()

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

    @SuppressLint("NotifyDataSetChanged")
    fun addAllData(dataList: ArrayList<AlarmDataEntity>) {
        if (this.dataList.size > 0) {
            this.dataList.clear()
        }
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val bind: ItemAlarmListBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: AlarmDataEntity) {
            bind.tvAmAndPm.text = if (data.hour > 12) "PM" else "AM"
            bind.tvHour.text = data.hour.toString()
            bind.tvMin.text = data.min.toString()
            bind.tvTime.text = String.format("${data.year}.${data.month}.${data.dayOfMonth}")
            bind.tvSoundFileName.text = data.soundFileName


            val colorSound = if (data.sound) {
                ResourcesCompat.getColor(bind.ivSound.resources, R.color.basicColor, null)
            } else {
                Color.RED
            }
            bind.ivSound.setColorFilter(colorSound)

            bind.llSound.setOnClickListener {
                dataUpdate(it.context, data, !data.sound, data.vibration, data.isActive)
            }

            val colorVibration = if (data.vibration) {
                ResourcesCompat.getColor(bind.ivVibration.resources, R.color.basicColor, null)
            } else {
                Color.RED
            }
            bind.ivVibration.setColorFilter(colorVibration)

            bind.llVibration.setOnClickListener {
                dataUpdate(it.context, data, data.sound, !data.vibration, data.isActive)
            }

            bind.swActive.isChecked = data.isActive
            bind.swActive.setOnCheckedChangeListener { compoundButton, b ->
                Log.d("Won", "활성화 $b")
                if (b) {

                } else {

                }
                dataUpdate(compoundButton.context, data, data.sound, data.vibration, b)
            }

            bind.ivDelete.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    AppDatabase.getInstance(it.context)?.getAlarmDao()?.delete(data.uid)
                }
            }
        }

        private fun dataUpdate(
            context: Context,
            data: AlarmDataEntity,
            sound: Boolean,
            vibration: Boolean,
            isActive: Boolean
        ) {
            GlobalScope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(context)?.getAlarmDao()?.update(
                    data.uid,
                    data.hour,
                    data.min,
                    data.year,
                    data.month,
                    data.dayOfMonth,
                    sound,
                    vibration,
                    data.soundFileName,
                    isActive
                )
            }
        }
    }
}