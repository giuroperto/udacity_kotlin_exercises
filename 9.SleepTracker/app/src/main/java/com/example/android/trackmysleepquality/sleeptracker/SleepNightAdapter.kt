package com.example.android.trackmysleepquality.sleeptracker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter : RecyclerView.Adapter<TextItemViewHolder>() {

    val data = listOf<SleepNight>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {

        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()

    }

    override fun getItemCount(): Int = data.size
}

